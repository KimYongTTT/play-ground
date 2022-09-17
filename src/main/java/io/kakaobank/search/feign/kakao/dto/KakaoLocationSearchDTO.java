package io.kakaobank.search.feign.kakao.dto;

import static io.kakaobank.search.utility.StringUtility.removeHtmlTags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.kakaobank.search.model.dto.response.Location;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
                .map(document -> new Location(removeHtmlTags(document.placeName)))
                .collect(Collectors.toList());
    }
}
