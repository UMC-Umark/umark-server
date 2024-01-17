package umc.project.umark.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { GlobalException.class})
    protected ResponseEntity handleCustomException(GlobalException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ApiResponse.ErrorResponse(e.getErrorCode());
    }

}