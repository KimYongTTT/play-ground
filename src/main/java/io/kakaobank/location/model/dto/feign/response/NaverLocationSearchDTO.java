package io.kakaobank.location.model.dto.feign.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class NaverLocationSearchDTO {
    @JsonProperty("channel")
    private Channel channel;

    public static class Channel {
        @JsonProperty("items")
        private Items[] items;

        public void setItems(Items[] items) {
            this.items = items;
        }

        public Items[] getItems() {
            return items;
        }
    }

    public static class Items {
        @JsonProperty("title")
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public List<String> toLocationNameList() {
        return Arrays.stream(channel.items).map(item -> item.title).collect(Collectors.toList());
    }
}
