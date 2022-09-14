package io.kakaobank.location.repository;

import io.kakaobank.location.model.dto.response.Top10KeywordsDTO;
import io.kakaobank.location.model.entity.SearchKeyword;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Long> {
    @Query(
            value =
                    "SELECT id, keyword, hit FROM kakaobank.search_keyword s ORDER BY hit DESC LIMIT 10",
            nativeQuery = true)
    List<Top10KeywordsDTO> findTop10Keywords();

    Optional<SearchKeyword> findByKeyword(String keyword);
}
