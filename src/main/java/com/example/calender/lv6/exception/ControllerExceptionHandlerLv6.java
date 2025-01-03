package com.example.calender.lv6.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandlerLv6 {

    @ExceptionHandler(ApiExceptionLv6.class)
    public ResponseEntity<ErrorResponseLv6> handleApiException(ApiExceptionLv6 e) {

        HttpStatus status = e.getStatus();

        return new ResponseEntity<>(
                new ErrorResponseLv6(status.value(), e.getMessage()),
                status
        );
    }

    /**
     * @Valid를 통해 검사가 실패되었을 경우 발생한 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseLv6> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        HttpStatusCode statusCode = e.getStatusCode();

        List<String> errorMessages = e.getFieldErrors().stream()
                .map(error -> error.getField() + " - " + error.getDefaultMessage())
                .toList();

        return new ResponseEntity<>(
                new ErrorResponseLv6(statusCode.value(), String.join(", ", errorMessages)),
                statusCode
        );
    }

    /**
     * 필수 값인 RequestParam 이 입력되지 않았을경우 발생한 예외 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseLv6> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        HttpStatusCode statusCode = e.getStatusCode();
        String parameterName = e.getParameterName();

        return new ResponseEntity<>(
                new ErrorResponseLv6(statusCode.value(), "필수 파라미터가 존재하지 않습니다. - " + parameterName),
                statusCode
        );
    }
}
