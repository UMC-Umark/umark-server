package umc.project.umark.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public static ApiResponse ErrorResponse(int statusCode, String message) {
        return ApiResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .data("")
                .build();
    }

    public static ResponseEntity ErrorResponse(GlobalErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .message(errorCode.getMessage())
                        .data("")
                        .build()
                );
    }

    public static <T> ResponseEntity SuccessResponse(String message, T data){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(message)
                        .data(data)
                        .build()
                );
    }

}