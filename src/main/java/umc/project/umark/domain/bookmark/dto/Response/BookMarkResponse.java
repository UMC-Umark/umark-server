package umc.project.umark.domain.bookmark.dto.Response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



public class BookMarkResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookMarkCreateResponseDTO{
        Long BookMarkId;
        LocalDateTime createdAt;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookMarkLikeResponseDTO{
        Long BookMarkId;
        Integer likeCount;
        LocalDateTime createdAt;
    }
}
