package io.kakaobank.location.utility;

import io.kakaobank.location.model.common.ResponseData;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtility {
    public ResponseEntity<ResponseData> createGetSuccessResponse(Object data) {
        return new ResponseEntity<>(
                ResponseData.builder().message("Success").data(data).build(), HttpStatus.OK);
    }
}
