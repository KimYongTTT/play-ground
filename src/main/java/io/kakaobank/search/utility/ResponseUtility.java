package io.kakaobank.search.utility;

import io.kakaobank.search.model.common.ResponseData;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtility {
    public ResponseEntity<ResponseData> createGetSuccessResponse(Object data) {
        return new ResponseEntity<>(ResponseData.successResponseData(data), HttpStatus.OK);
    }

    public ResponseEntity<ResponseData> createGetFailResponse(String message) {
        return new ResponseEntity<>(ResponseData.failResponseData(message), HttpStatus.NOT_FOUND);
    }
}
