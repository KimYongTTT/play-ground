package io.kakaobank.location.service;

import io.kakaobank.location.feign.kakao.KakaoFeignClient;
import io.kakaobank.location.feign.naver.NaverFeignClient;
import io.kakaobank.location.model.dto.response.Keyword;
import io.kakaobank.location.model.dto.response.Location;
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

    private static final Integer LOCATION_SEARCH_RESULT_SIZE = 10;
    private static final Integer NAVER_MAX_PAGING_SIZE = 5;

    // TODO spring api caching 이용
    // API 자체의 exception handle --> empty result maybe? kakao and naver both
    // open feign resillienceJ circuit breaker 추가 완료, read time out 에 대한 처리..?
    @Transactional
    public List<Location> searchLocation(final String keyword) {
        searchKeywordRepository.insertOrUpdateKeywordHit(keyword);

        List<Location> naverResult = naverLocationSearchBy(keyword, NAVER_MAX_PAGING_SIZE);
        List<Location> kakaoResult =
                kakaoLocationSearchBy(keyword, LOCATION_SEARCH_RESULT_SIZE - naverResult.size());

        log.info(kakaoResult.toString());
        log.info(naverResult.toString());

        return sortAndCombineSearchResults(kakaoResult, naverResult);
    }

    @Transactional(readOnly = true)
    public List<Keyword> getKeywords() {
        return searchKeywordRepository.findTop10ByOrderByHitDesc().stream()
                .map(Keyword::from)
                .collect(Collectors.toList());
    }

    public List<Location> kakaoLocationSearchBy(final String keyword, final Integer pageSize) {
        return kakaoFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }

    public List<Location> naverLocationSearchBy(final String keyword, final Integer pageSize) {
        return naverFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }

    private <T> List<T> sortAndCombineSearchResults(
            List<T> kakaoResult, List<T>... otherSeachResults) {
        LinkedHashSet<T> combinedResult = new LinkedHashSet<>(kakaoResult);

        for (List<T> otherResult : otherSeachResults) {
            combinedResult.retainAll(otherResult);
        }

        combinedResult.addAll(kakaoResult);

        for (List<T> otherResult : otherSeachResults) {
            combinedResult.addAll(otherResult);
        }

        return combinedResult.stream().collect(Collectors.toList());
    }
}
