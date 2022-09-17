package io.kakaobank.search.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseData {

    private Boolean isSuccess;
    private String message;
    private Object data;

    @Builder
    public ResponseData(Boolean isSuccess, String message, Object data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static ResponseData successResponseData(Object data) {
        return ResponseData.builder().isSuccess(true).message("").data(data).build();
    }

    public static ResponseData failResponseData(String message) {
        return ResponseData.builder().isSuccess(false).message(message).build();
    }
}
