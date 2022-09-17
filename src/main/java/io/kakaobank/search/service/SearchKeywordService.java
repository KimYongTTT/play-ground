package io.kakaobank.search.service;

import io.kakaobank.search.model.dto.response.Keyword;
import io.kakaobank.search.repository.SearchKeywordRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional
    public void addKeyword(final String keyword) {
        searchKeywordRepository.insertOrUpdateKeywordHit(keyword);
    }

    @Transactional(readOnly = true)
    public List<Keyword> getTop10Keywords() {
        return searchKeywordRepository.findTop10ByOrderByHitDesc().stream()
                .map(Keyword::from)
                .collect(Collectors.toList());
    }
}
