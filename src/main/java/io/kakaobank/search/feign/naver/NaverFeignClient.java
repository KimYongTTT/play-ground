package io.kakaobank.search.feign.naver;

import io.kakaobank.search.feign.naver.config.NaverFeignConfig;
import io.kakaobank.search.feign.naver.dto.NaverLocationSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverFeignUtility",
        url = "${app.api.naver.url}",
        configuration = NaverFeignConfig.class,
        fallbackFactory = NaverFeignClientFallbackFactory.class)
public interface NaverFeignClient {
    @GetMapping(path = "/v1/search/local.json")
    NaverLocationSearchDTO searchLocationByKeyword(
            @RequestParam String query, @RequestParam(value = "display") Integer pageSize);
}
