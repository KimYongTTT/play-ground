package io.kakaobank.location.restcontroller;

import io.kakaobank.location.model.common.ResponseData;
import io.kakaobank.location.service.LocationSearchService;
import io.kakaobank.location.utility.ResponseUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationSearchRestController {
    private final LocationSearchService locationSearchService;

    @GetMapping(path = "/v1/kakaobank/search/locations")
    public ResponseEntity<ResponseData> searchLocation(@RequestParam String keyword) {
        return ResponseUtility.createGetSuccessResponse(
                locationSearchService.searchLocation(keyword));
    }

    @GetMapping(path = "/v1/kakaobank/search/keywords")
    public ResponseEntity<ResponseData> getKeywords() {
        return ResponseUtility.createGetSuccessResponse(locationSearchService.getKeywords());
    }
}
