package io.kakaobank.location.model.entity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "search_keyword_id")
    private SearchKeyword keyword;

    @Column(name = "search_datetime", updatable = false)
    private OffsetDateTime searchDatetime;

    @Builder
    public SearchHistory(SearchKeyword keyword) {
        this.keyword = keyword;
        this.searchDatetime = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public static SearchHistory newSearchHistory(SearchKeyword keyword) {
        return SearchHistory.builder().keyword(keyword).build();
    }
}
