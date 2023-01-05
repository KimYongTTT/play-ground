package io.playground.search.service;

import static io.playground.search.constants.CacheName.LOCATION_CACHE;
import static io.playground.search.constants.SearchConstants.*;

import io.playground.search.feign.kakao.KakaoFeignClient;
import io.playground.search.feign.naver.NaverFeignClient;
import io.playground.search.model.dto.Location;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationSearchService implements BaseSearchService {
    private final KakaoFeignClient kakaoFeignClient;
    private final NaverFeignClient naverFeignClient;

    @Override
    @Cacheable(value = LOCATION_CACHE, key = "#keyword")
    public List<Location> searchByKeyword(final String keyword) {
        List<Location> naverResult = naverLocationSearchBy(keyword, DEFAULT_PAGING_SIZE);
        List<Location> kakaoResult =
                kakaoLocationSearchBy(keyword, LOCATION_SEARCH_RESULT_SIZE - naverResult.size());

        // ONLY FOR TESTING
        log.info("Result From Naver " + naverResult);
        log.info("Result From Kakao " + kakaoResult);

        return sortAndCombineSearchResults(kakaoResult, naverResult);
    }

    public List<Location> kakaoLocationSearchBy(final String keyword, final Integer pageSize) {
        return kakaoFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }

    public List<Location> naverLocationSearchBy(final String keyword, final Integer pageSize) {
        return naverFeignClient.searchLocationByKeyword(keyword, pageSize).toLocationList();
    }
}
