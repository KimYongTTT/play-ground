package io.kakaobank.search.model.dto.response;

import io.kakaobank.search.model.entity.SearchKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Keyword {
    private String keyword;

    private Long hit;

    @Builder
    public Keyword(String keyword, Long hit) {
        this.keyword = keyword;
        this.hit = hit;
    }

    public static Keyword from(SearchKeyword searchKeyword) {
        return Keyword.builder()
                .keyword(searchKeyword.getKeyword())
                .hit(searchKeyword.getHit())
                .build();
    }
}
