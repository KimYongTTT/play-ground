package io.playground.search.model.dto;

import io.playground.search.model.entity.SearchKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Keyword {
    @Schema(name = "keyword", example = "서울")
    private String keyword;

    @Schema(name = "hit", description = "검색 횟수", example = "1000")
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
