package io.kakaobank.search.model.entity;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor
public class SearchKeyword {
    @Id
    @Column(name = "keyword")
    private String keyword;

    @Column(name = "hit")
    private Long hit;

    @UpdateTimestamp
    @Column(name = "last_search_datetime")
    private OffsetDateTime lastSearchDatetime;

    @Builder
    public SearchKeyword(String keyword, Long hit) {
        this.keyword = keyword;
        this.hit = hit;
    }

    public static SearchKeyword newSearchKeyword(String keyword) {
        return SearchKeyword.builder().keyword(keyword).hit(1L).build();
    }

    public void increaseHit() {
        hit = hit + 1;
    }
}
