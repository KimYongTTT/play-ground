package io.playground.search.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseSearchService<T> {
    List<T> searchByKeyword(final String keyword);

    default List<T> sortAndCombineSearchResults(List<T> kakaoResult, List<T>... otherSeachResults) {
        LinkedHashSet<T> combinedResult = new LinkedHashSet<>(kakaoResult);

        for (List<T> otherResult : otherSeachResults) {
            combinedResult.retainAll(otherResult);
        }

        combinedResult.addAll(kakaoResult);

        for (List<T> otherResult : otherSeachResults) {
            combinedResult.addAll(otherResult);
        }

        return combinedResult.stream().collect(Collectors.toList());
    }
}
