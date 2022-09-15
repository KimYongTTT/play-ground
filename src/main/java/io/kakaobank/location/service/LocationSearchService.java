package io.kakaobank.location.service;

import io.kakaobank.location.feign.KakaoFeignClient;
import io.kakaobank.location.feign.NaverFeignClient;
import io.kakaobank.location.model.dto.response.KeywordListDTO;
import io.kakaobank.location.model.dto.response.LocationSearchDTO;
import io.kakaobank.location.model.dto.response.SearchResult;
import io.kakaobank.location.repository.SearchKeywordRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
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
    private final SearchKeywordRepository searchKeywordRepository;

    private static final Integer MAX_PAGING_SIZE = 5;

    // TODO spring api caching 이용
    // open feign circuit breaker 이용, 연동 API 장애 handle
    // API 자체의 exception handle
    //
    @Transactional
    public LocationSearchDTO searchLocation(final String keyword) {
        searchKeywordRepository.insertOrUpdateKeywordHit(keyword);

        List<SearchResult> kakaoResult = kakaoLocationSearchBy(keyword, MAX_PAGING_SIZE);
        List<SearchResult> naverResult = naverLocationSearchBy(keyword, MAX_PAGING_SIZE);
        log.info(kakaoResult.toString());
        log.info(naverResult.toString());

        return LocationSearchDTO.builder()
                .locations(sortAndCombineSearchResults(kakaoResult, naverResult))
                .build();
    }

    @Transactional(readOnly = true)
    public List<KeywordListDTO> getKeywords() {
        return searchKeywordRepository.findTop10ByOrderByHitDesc().stream()
                .map(KeywordListDTO::from)
                .collect(Collectors.toList());
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
