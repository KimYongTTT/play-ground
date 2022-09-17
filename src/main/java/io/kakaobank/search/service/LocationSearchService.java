package io.kakaobank.search.service;

import static io.kakaobank.search.constants.CacheName.LOCATION_CACHE;
import static io.kakaobank.search.constants.SearchConstants.LOCATION_SEARCH_RESULT_SIZE;
import static io.kakaobank.search.constants.SearchConstants.NAVER_MAX_PAGING_SIZE;

import io.kakaobank.search.feign.kakao.KakaoFeignClient;
import io.kakaobank.search.feign.naver.NaverFeignClient;
import io.kakaobank.search.model.dto.response.Location;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationSearchService implements BaseSearchService {
    private final KakaoFeignClient kakaoFeignClient;
    private final NaverFeignClient naverFeignClient;
    private final CacheManager cacheManager;

    // TODO spring api caching 이용
    // API 자체의 exception handle --> empty result maybe? kakao and naver both
    // open feign resillienceJ circuit breaker 추가 완료, read time out 에 대한 처리..?

    @Override
    @Cacheable(value = LOCATION_CACHE, key = "#keyword")
    public List<Location> searchByKeyword(final String keyword) {
        List<Location> naverResult = naverLocationSearchBy(keyword, NAVER_MAX_PAGING_SIZE);
        List<Location> kakaoResult =
                kakaoLocationSearchBy(keyword, LOCATION_SEARCH_RESULT_SIZE - naverResult.size());

        log.info(kakaoResult.toString());
        log.info(naverResult.toString());
        List<Location> returnLocationList = sortAndCombineSearchResults(kakaoResult, naverResult);

        // CacheUpdate
        cacheManager.getCache(LOCATION_CACHE).putIfAbsent(keyword, returnLocationList);

        return returnLocationList;
    }

    public List<Location> kakaoLocationSearchBy(final String keyword, final Integer pageSize) {
        return kakaoFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }

    public List<Location> naverLocationSearchBy(final String keyword, final Integer pageSize) {
        return naverFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }
}
