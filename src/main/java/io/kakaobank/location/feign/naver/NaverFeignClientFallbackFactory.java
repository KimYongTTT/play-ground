package io.kakaobank.location.feign.naver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverFeignClientFallbackFactory implements FallbackFactory<NaverFeignClient> {
    private final NaverFeignClientFallback naverFeignClientFallback;

    @Override
    public NaverFeignClient create(Throwable cause) {
        log.info("[Naver API] Naver API call fallback cause : {}", cause.getMessage());
        return naverFeignClientFallback;
    }
}
