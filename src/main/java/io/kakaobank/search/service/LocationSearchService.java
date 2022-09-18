package io.kakaobank.search.service;

import static io.kakaobank.search.constants.CacheName.LOCATION_CACHE;
import static io.kakaobank.search.constants.SearchConstants.*;

import io.kakaobank.search.feign.kakao.KakaoFeignClient;
import io.kakaobank.search.feign.naver.NaverFeignClient;
import io.kakaobank.search.model.dto.response.Location;
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

        //ONLY FOR TESTING
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
