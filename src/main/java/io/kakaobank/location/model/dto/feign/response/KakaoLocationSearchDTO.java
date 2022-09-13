package io.kakaobank.location.model.dto.feign.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class KakaoLocationSearchDTO {
    @JsonProperty("documents")
    private Document[] documents;

    public static class Document {
        @JsonProperty("place_name")
        private String placeName;

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getPlaceName() {
            return placeName;
        }
    }

    @Builder
    public KakaoLocationSearchDTO(Document[] documents) {
        this.documents = documents;
    }

    public List<String> toLocationNameList() {
        return Arrays.stream(documents)
                .map(document -> document.placeName)
                .collect(Collectors.toList());
    }
}
