package umc.project.umark.domain.mapping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class BookMarkLikeResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookMarkLikeResponseDTO{
        Long bookMarkId;
        Integer likeCount;
        LocalDateTime createdAt;
    }
}
