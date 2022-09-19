package io.kakaobank.search.restcontroller;

import io.kakaobank.search.exception.BusinessException;
import io.kakaobank.search.model.common.BaseResponse;
import io.kakaobank.search.model.dto.Keyword;
import io.kakaobank.search.model.dto.Location;
import io.kakaobank.search.service.LocationSearchService;
import io.kakaobank.search.service.SearchKeywordService;
import io.kakaobank.search.utility.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Search", description = "Kakaobank 검색 API")
@RestController
@RequiredArgsConstructor
@Validated
public class SearchRestController {
    private final LocationSearchService locationSearchService;

    private final SearchKeywordService searchKeywordService;

    @Operation(summary = "키워드로 장소를 검색 후 결과 목록을 반환한다.")
    @Parameter(name = "keyword", description = "검색 키워드")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Location.class)))
    @GetMapping(path = "/v1/kakaobank/search/locations")
    public ResponseEntity<BaseResponse> searchLocation(@RequestParam @NotBlank String keyword) {
        searchKeywordService.addKeyword(keyword);
        List<Location> responseData = locationSearchService.searchByKeyword(keyword.trim());

        if (responseData.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Result Not Found");
        }

        return ResponseUtility.createGetSuccessResponse(
                locationSearchService.searchByKeyword(keyword.trim()));
    }

    @Operation(summary = "상위 10개 검색키워드 목록을 반환한다.")
    @ApiResponse(content = @Content(schema = @Schema(implementation = Keyword.class)))
    @GetMapping(path = "/v1/kakaobank/search/keywords")
    public ResponseEntity<BaseResponse> getKeywords() {
        return ResponseUtility.createGetSuccessResponse(searchKeywordService.getTop10Keywords());
    }
}
