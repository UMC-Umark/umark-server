package umc.project.umark.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import umc.project.umark.global.common.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = { GlobalException.class})
    protected ApiResponse handleCustomException(GlobalException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
         return ApiResponse.onFailure(e.getErrorCode(), "");
    }


}