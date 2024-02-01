package umc.project.umark.domain.term.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TermDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TermRequestDto {
        String title;
        String description;
        Boolean isCrucial;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TermResponseDto {
        Long id;
        String title;
        String description;
        Boolean isCrucial;
    }
}
