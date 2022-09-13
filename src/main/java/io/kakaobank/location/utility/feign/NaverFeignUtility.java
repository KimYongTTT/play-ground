package io.kakaobank.location.utility.feign;

import io.kakaobank.location.config.NaverFeignConfig;
import io.kakaobank.location.model.dto.feign.response.NaverLocationSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverFeignUtility",
        url = "${app.api.naver.url}",
        configuration = NaverFeignConfig.class)
public interface NaverFeignUtility {
    @GetMapping(path = "/v1/search/local.json")
    NaverLocationSearchDTO searchLocationByKeyword(
            @RequestParam String query, @RequestParam(value = "display") Integer pageSize);
}
