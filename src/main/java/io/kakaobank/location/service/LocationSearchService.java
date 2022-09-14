package io.kakaobank.location.service;

import io.kakaobank.location.feign.KakaoFeignClient;
import io.kakaobank.location.feign.NaverFeignClient;
import io.kakaobank.location.model.dto.response.LocationSearchDTO;
import io.kakaobank.location.model.dto.response.SearchResult;
import io.kakaobank.location.model.dto.response.Top10KeywordsDTO;
import io.kakaobank.location.model.entity.SearchHistory;
import io.kakaobank.location.model.entity.SearchKeyword;
import io.kakaobank.location.repository.SearchHistoryRepository;
import io.kakaobank.location.repository.SearchKeywordRepository;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationSearchService {
    private final KakaoFeignClient kakaoFeignClient;
    private final NaverFeignClient naverFeignClient;

    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchKeywordRepository searchKeywordRepository;

    private static final Integer MAX_PAGING_SIZE = 5;

    // TODO spring api caching 이용
    // open feign circuit breaker 이용, 연동 API 장애 handle
    // API 자체의 exception handle
    //
    @Transactional
    public LocationSearchDTO searchLocation(final String keyword) {
        saveKeyword(keyword);

        List<SearchResult> kakaoResult = kakaoLocationSearchBy(keyword, MAX_PAGING_SIZE);
        List<SearchResult> naverResult = naverLocationSearchBy(keyword, MAX_PAGING_SIZE);
        log.info(kakaoResult.toString());
        log.info(naverResult.toString());

        return LocationSearchDTO.builder()
                .locations(sortAndCombineSearchResults(kakaoResult, naverResult))
                .build();
    }

    // TODO dto projection , queryDSL 사용?
    @Transactional(readOnly = true)
    public List<Top10KeywordsDTO> getKeywords() {
        return searchKeywordRepository.findTop10Keywords();
    }

    // TODO 리팩토링
    // hit 횟수 update 동시성 문제 handle, query 사용, save 로 할경우 merge 가 나가고있음.
    public void saveKeyword(final String keyword) {
        SearchKeyword searchKeyword =
                searchKeywordRepository
                        .findByKeyword(keyword)
                        .orElse(SearchKeyword.newSearchKeyword(keyword));

        searchKeyword.hit();
        searchKeywordRepository.save(searchKeyword);

        SearchHistory newSearchHistory = SearchHistory.newSearchHistory(searchKeyword);
        searchHistoryRepository.save(newSearchHistory);
    }

    public List<SearchResult> kakaoLocationSearchBy(final String keyword, final Integer pageSize) {
        return kakaoFeignClient.searchLocationByKeyword(keyword, pageSize).toResultList();
    }

    public List<SearchResult> naverLocationSearchBy(final String keyword, final Integer pageSize) {
        return naverFeignClient.searchLocationByKeyword(keyword, pageSize).toResultList();
    }

    private LinkedHashSet<SearchResult> sortAndCombineSearchResults(
            List<SearchResult> kakaoResult, List<SearchResult> naverResult) {
        LinkedHashSet<SearchResult> combinedResult = new LinkedHashSet<>(kakaoResult);

        combinedResult.retainAll(naverResult);
        combinedResult.addAll(kakaoResult);
        combinedResult.addAll(naverResult);

        return combinedResult;
    }
}
