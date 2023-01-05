package io.playground.search.utility;

import io.playground.search.model.common.BaseResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtility {
    public ResponseEntity<BaseResponse> createGetSuccessResponse(Object data) {
        return new ResponseEntity<>(BaseResponse.successResponse(data), HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> createFailResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(BaseResponse.failResponse(message), httpStatus);
    }
}
