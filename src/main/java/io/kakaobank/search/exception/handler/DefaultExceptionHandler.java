package io.kakaobank.search.exception.handler;

import io.kakaobank.search.exception.BusinessException;
import io.kakaobank.search.model.common.BaseResponse;
import javax.validation.ConstraintViolationException;

import io.kakaobank.search.utility.ResponseUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse> handleException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return ResponseUtility.createFailResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleException(BusinessException ex) {
        log.error(ex.getMessage());
        return ResponseUtility.createFailResponse(ex.getMessage(), ex.getCode());
    }
}
