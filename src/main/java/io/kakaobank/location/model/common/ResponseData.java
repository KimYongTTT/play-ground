package io.kakaobank.location.model.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseData {
    private String successOrNot;
    private Object data;

    @Builder
    public ResponseData(String successOrNot, Object data) {
        this.successOrNot = successOrNot;
        this.data = data;
    }
}
