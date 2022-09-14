package io.kakaobank.location.feign.dto.response;

import static io.kakaobank.location.utility.StringUtility.removeHtmlTags;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.kakaobank.location.model.dto.response.SearchResult;
import java.util.Arrays;
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

    public List<SearchResult> toResultList() {
        return Arrays.stream(documents)
                .map(document -> new SearchResult(removeHtmlTags(document.placeName)))
                .collect(Collectors.toList());
    }
}
