package io.kakaobank.location.repository;

import io.kakaobank.location.model.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {}
