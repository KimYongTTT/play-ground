package io.playground.search.feign.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoFeignClientFallbackFactory implements FallbackFactory<KakaoFeignClient> {

    private final KakaoFeignClientFallback kaKaoFeignClientFallback;

    @Override
    public KakaoFeignClient create(Throwable cause) {
        log.info("[Kakao API] Kakao API call fallback cause : {}", cause.getMessage());
        return kaKaoFeignClientFallback;
    }
}
