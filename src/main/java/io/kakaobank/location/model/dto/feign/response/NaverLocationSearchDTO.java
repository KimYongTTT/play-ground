package io.kakaobank.location.model.dto.feign.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.kakaobank.location.model.dto.response.SearchResult;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

import static io.kakaobank.location.utility.StringUtility.removeHtmlTags;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class NaverLocationSearchDTO {
    @JsonProperty("items")
    private Item[] items;

    public static class Item {
        @JsonProperty("title")
        private String title;
    }

    public List<SearchResult> toResultList() {
        return Arrays.stream(items)
                .map(item -> new SearchResult(removeHtmlTags(item.title)))
                .collect(Collectors.toList());
    }
}
