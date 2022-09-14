package io.kakaobank.location.feign;

import io.kakaobank.location.config.KakaoFeignConfig;
import io.kakaobank.location.feign.dto.response.KakaoLocationSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoFeign",
        url = "${app.api.kakao.url}",
        configuration = KakaoFeignConfig.class)
public interface KakaoFeignClient {
    @GetMapping(path = "/v2/local/search/keyword.json")
    KakaoLocationSearchDTO searchLocationByKeyword(
            @RequestParam String query, @RequestParam(value = "size") Integer pageSize);
}
