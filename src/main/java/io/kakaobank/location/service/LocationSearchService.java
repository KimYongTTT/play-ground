package io.kakaobank.location.service;

import io.kakaobank.location.model.dto.response.LocationSearchDTO;
import io.kakaobank.location.utility.feign.KakaoFeignUtility;
import io.kakaobank.location.utility.feign.NaverFeignUtility;
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

    private static final Integer MAX_SEARCH_LIMIT = 5;

    public LocationSearchDTO searchLocation(final String keyword) {

        List<String> kakaoResult =
                kakaoFeignUtility.searchLocationByKeyword(keyword, MAX_SEARCH_LIMIT).toLocationNameList();
        log.info(kakaoResult.toString());
        List<String> naverResult =
                naverFeignUtility.searchLocationByKeyword(keyword, MAX_SEARCH_LIMIT).toLocationNameList();
        log.info(naverResult.toString());
        return null;
    }

    public void getKeywords() {}
}
