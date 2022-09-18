package com.mungta.user.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import com.mungta.user.dto.ResponseDto;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[handleMethodArgumentNotValidException] ", e);
        return ResponseEntity.badRequest()
                .body(ResponseDto.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .message(e.getFieldErrors().get(0).getDefaultMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<ResponseDto> handleMethodNotAllowedException(MethodNotAllowedException e) {
        log.error("[handleMethodArgumentNotValidException] ", e);
        return new ResponseEntity<>(
          ResponseDto.builder()
                        .errorCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message("해당 요청을 처리할 수 없습니다.")
                        .build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ResponseDto> handleApiException(ApiException e) {
        log.error("[ApiException] ", e);
        ApiStatus apiStatus = e.getApiStatus();
        return new ResponseEntity<>(ResponseDto.of(apiStatus), apiStatus.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ResponseDto> handleRuntimeException(RuntimeException e) {
        log.error("[handleRuntimeException] ", e);
        return ResponseEntity.internalServerError()
                .body(ResponseDto.of(ApiStatus.UNEXPECTED_ERROR));
    }

}
