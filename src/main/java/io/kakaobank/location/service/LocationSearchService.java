package io.kakaobank.location.service;

import io.kakaobank.location.model.dto.response.LocationSearchDTO;
import io.kakaobank.location.model.dto.response.SearchResult;
import io.kakaobank.location.utility.feign.KakaoFeignUtility;
import io.kakaobank.location.utility.feign.NaverFeignUtility;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationSearchService {
    private final KakaoFeignUtility kakaoFeignUtility;
    private final NaverFeignUtility naverFeignUtility;

    private static final Integer MAX_PAGING_SIZE = 5;

    //TODO spring api caching 이용
    // open feign circuit breaker 이용, 연동 API 장애 handle
    // API 자체의 exception handle
    //
    public LocationSearchDTO searchLocation(final String keyword) {
        List<SearchResult> kakaoResult = kakaoLocationSearchBy(keyword, MAX_PAGING_SIZE);
        List<SearchResult> naverResult = naverLocationSearchBy(keyword, MAX_PAGING_SIZE);
        log.info(kakaoResult.toString());
        log.info(naverResult.toString());

        return LocationSearchDTO.builder()
                .locations(sortAndCombineSearchResults(kakaoResult, naverResult))
                .build();
    }

    public void getKeywords() {
        //TODO JPA 이용, keyword 검색 횟수 조회?
        // OR Redis 이용(TTL 로 최근검색 보장)
    }

    public List<SearchResult> kakaoLocationSearchBy(final String keyword, final Integer pageSize) {
        return kakaoFeignUtility.searchLocationByKeyword(keyword, pageSize).toResultList();
    }

    public List<SearchResult> naverLocationSearchBy(final String keyword, final Integer pageSize) {
        return naverFeignUtility.searchLocationByKeyword(keyword, pageSize).toResultList();
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
