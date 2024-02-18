package umc.project.umark.domain.report.dto.Request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.project.umark.domain.hashtag.entity.HashTag;

import java.util.List;

public class ReportRequest {
    @Getter
    public static class ReportRequestDTO{

        private Long bookMarkId;
        private Integer report;
        private String reason;

    }
}
