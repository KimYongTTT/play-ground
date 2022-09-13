package io.kakaobank.location.model.dto.response;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationSearchDTO {
    private Collection<SearchResult> locations;
}
