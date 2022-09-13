package io.kakaobank.location.model.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationSearchDTO {
    private List<String> searchResultList;
}
