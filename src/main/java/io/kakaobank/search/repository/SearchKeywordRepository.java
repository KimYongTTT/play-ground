package io.kakaobank.search.repository;

import io.kakaobank.search.model.entity.SearchKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderByHitDesc();

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value =
                    "INSERT INTO kakaobank.search_keyword (keyword) value (:keyword) ON DUPLICATE KEY UPDATE hit=hit+1",
            nativeQuery = true)
    Integer insertOrUpdateKeywordHit(String keyword);
}
