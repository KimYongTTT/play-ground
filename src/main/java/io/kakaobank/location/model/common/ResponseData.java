package io.kakaobank.location.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseData {
    private String message;
    private Object data;

    @Builder
    public ResponseData(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
