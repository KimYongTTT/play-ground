package io.playground.search.feign.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.playground.search.model.dto.Location;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.playground.search.utility.StringUtility;
import lombok.*;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLocationSearchDTO {
    @JsonProperty("documents")
    private Document[] documents;

    public static class Document {
        @JsonProperty("place_name")
        private String placeName;
    }

    public static KakaoLocationSearchDTO empty() {
        return new KakaoLocationSearchDTO();
    }

    public List<Location> toLocationList() {
        if (documents == null) return Collections.emptyList();

        return Arrays.stream(documents)
                .map(document -> new Location(StringUtility.removeHtmlTags(document.placeName)))
                .collect(Collectors.toList());
    }
}
