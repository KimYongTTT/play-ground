package io.kakaobank.location.model.entity;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SearchKeyword {
    @Id
    @Column(name = "keyword")
    private String keyword;

    @Column(name = "hit")
    private Long hit;

    @Column(name = "last_search_datetime")
    private OffsetDateTime lastSearchDatetime;

    @Builder
    public SearchKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static SearchKeyword newSearchKeyword(String keyword) {
        return SearchKeyword.builder().keyword(keyword).build();
    }
}
