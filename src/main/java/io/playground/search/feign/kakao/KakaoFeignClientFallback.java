package io.playground.search.feign.kakao;

import io.playground.search.feign.kakao.dto.KakaoLocationSearchDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KakaoFeignClientFallback implements KakaoFeignClient {
    @Override
    public KakaoLocationSearchDTO searchLocationByKeyword(String query, Integer pageSize) {
        log.info("[Kakao API] Keyword Location Search Failed, keyword =[{}]", query);
        return KakaoLocationSearchDTO.empty();
    }
}
