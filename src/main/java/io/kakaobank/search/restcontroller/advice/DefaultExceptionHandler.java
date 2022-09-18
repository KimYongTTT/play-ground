package io.kakaobank.search.restcontroller.advice;

import io.kakaobank.search.model.common.ResponseData;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResponseData> handleException(Exception ex) {
        ResponseData responseData =
                ResponseData.builder().isSuccess(false).message(ex.getMessage()).build();
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
