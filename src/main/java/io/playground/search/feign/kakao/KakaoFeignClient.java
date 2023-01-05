package io.playground.search.feign.kakao;

import io.playground.search.feign.kakao.config.KakaoFeignConfig;
import io.playground.search.feign.kakao.dto.KakaoLocationSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "kakaoFeign",
        url = "${app.api.kakao.url}",
        configuration = KakaoFeignConfig.class,
        fallbackFactory = KakaoFeignClientFallbackFactory.class)
public interface KakaoFeignClient {
    @GetMapping(path = "/v2/local/search/keyword.json")
    KakaoLocationSearchDTO searchLocationByKeyword(
            @RequestParam String query, @RequestParam(value = "size") Integer pageSize);
}
