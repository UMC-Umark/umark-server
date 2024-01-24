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
        String description;
        Boolean isAgree;
        Boolean isCrucial;
    }
}
