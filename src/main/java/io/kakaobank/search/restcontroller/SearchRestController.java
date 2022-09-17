package io.kakaobank.search.restcontroller;

import io.kakaobank.search.model.common.ResponseData;
import io.kakaobank.search.service.LocationSearchService;
import io.kakaobank.search.service.SearchKeywordService;
import io.kakaobank.search.utility.ResponseUtility;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class SearchRestController {
    private final LocationSearchService locationSearchService;

    private final SearchKeywordService searchKeywordService;

    @GetMapping(path = "/v1/kakaobank/search/locations")
    public ResponseEntity<ResponseData> searchLocation(@RequestParam @NotBlank String keyword) {
        searchKeywordService.addKeyword(keyword);
        return ResponseUtility.createGetSuccessResponse(
                locationSearchService.searchByKeyword(keyword.trim()));
    }

    @GetMapping(path = "/v1/kakaobank/search/keywords")
    public ResponseEntity<ResponseData> getKeywords() {
        return ResponseUtility.createGetSuccessResponse(searchKeywordService.getTop10Keywords());
    }
}
