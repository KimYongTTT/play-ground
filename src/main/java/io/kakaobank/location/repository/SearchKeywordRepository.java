package io.kakaobank.location.repository;

import io.kakaobank.location.model.entity.SearchKeyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    List<SearchKeyword> findTop10ByOrderByHitDesc();

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value =
                    "INSERT INTO kakaobank.search_keyword (keyword) value (:keyword) ON DUPLICATE KEY UPDATE hit=hit+1",
            nativeQuery = true)
    Integer insertOrUpdateKeywordHit(String keyword);
}
