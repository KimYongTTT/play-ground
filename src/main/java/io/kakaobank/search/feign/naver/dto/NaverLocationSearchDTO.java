package io.kakaobank.search.feign.naver.dto;

import static io.kakaobank.search.utility.StringUtility.removeHtmlTags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.kakaobank.search.model.dto.Location;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

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

    public static NaverLocationSearchDTO empty() {
        return new NaverLocationSearchDTO();
    }

    public List<Location> toLocationList() {
        if (items == null) return Collections.emptyList();

        return Arrays.stream(items)
                .map(item -> new Location(removeHtmlTags(item.title)))
                .collect(Collectors.toList());
    }
}
