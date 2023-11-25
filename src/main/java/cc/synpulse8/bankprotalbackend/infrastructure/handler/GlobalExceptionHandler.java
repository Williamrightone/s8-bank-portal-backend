package cc.synpulse8.bankprotalbackend.infrastructure.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException error) {

        List<FieldError> fieldErrors = error.getBindingResult().getFieldErrors();

        StringBuilder errorFields = new StringBuilder();

        for (FieldError fieldError : fieldErrors) {
            if (!errorFields.isEmpty()) {
                errorFields.append(", ");
            }
            errorFields.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST,
                        GlobalServiceException.GlobalServiceErrorType.INPUT_FORMAT_ERROR.getErrorCode()));

    }

    @ExceptionHandler(GlobalServiceException.class)
    public ResponseEntity<ResponseErrorDto> handleCustomServiceException(GlobalServiceException error) {

        if(error!= null && error.getErrorType().getErrorLevel().equals(GlobalErrorLevel.HIGH)) {
            log.error(error.getMessage());
            log.error(error.getErrorType().getErrorCode());

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST, error.getErrorType().getErrorCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseErrorDto> handleAccessDeniedException(AccessDeniedException error) {

        System.out.println("AccessDeniedException 接住");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST, "10001"));
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseErrorDto> handleAuthenticationException(AuthenticationException error) {

        System.out.println("AuthenticationException 接住");
        log.info(error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseErrorDto(HttpStatus.BAD_REQUEST, "10002"));
    }


}
