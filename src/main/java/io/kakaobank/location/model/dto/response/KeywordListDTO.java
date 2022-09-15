package io.kakaobank.location.model.dto.response;

import io.kakaobank.location.model.entity.SearchKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordListDTO {
    private String keyword;

    private Long hit;

    @Builder
    public KeywordListDTO(String keyword, Long hit) {
        this.keyword = keyword;
        this.hit = hit;
    }

    public static KeywordListDTO from(SearchKeyword searchKeyword) {
        return KeywordListDTO.builder()
                .keyword(searchKeyword.getKeyword())
                .hit(searchKeyword.getHit())
                .build();
    }
}
