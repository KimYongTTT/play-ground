package io.kakaobank.search.feign.naver;

import io.kakaobank.search.feign.naver.dto.NaverLocationSearchDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NaverFeignClientFallback implements NaverFeignClient {
    @Override
    public NaverLocationSearchDTO searchLocationByKeyword(String query, Integer pageSize) {
        log.info("[Naver API] Keyword Location Search Failed, keyword =[{}]", query);
        return NaverLocationSearchDTO.empty();
    }
}
