package io.kakaobank.search.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResponse {

    private Boolean isSuccess;
    private String message;
    private Object data;

    @Builder
    public BaseResponse(Boolean isSuccess, String message, Object data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse successResponse(Object data) {
        return BaseResponse.builder().isSuccess(true).message("").data(data).build();
    }

    public static BaseResponse failResponse(String message) {
        return BaseResponse.builder().isSuccess(false).message(message).build();
    }
}
