package umc.project.umark.domain.report.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.project.umark.domain.report.Report;

import java.time.LocalDateTime;

public class ReportResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportResponseDTO{

        Long reportId;
        String selectedType;
        LocalDateTime createdAt;

    }
}
