package io.kakaobank.location.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class SearchKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "keyword", nullable = false, unique = true)
    private String keyword;

    @Column(name = "hit", nullable = false)
    private Long hit;

    @Builder
    public SearchKeyword(String keyword) {
        this.keyword = keyword;
        this.hit = 0L;
    }

    public static SearchKeyword newSearchKeyword(String keyword) {
        return SearchKeyword.builder().keyword(keyword).build();
    }

    public void hit() {
        this.hit += 1;
    }
}
