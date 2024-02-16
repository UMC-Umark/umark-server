package umc.project.umark.domain.report.converter;

import lombok.extern.slf4j.Slf4j;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.report.Report;
import umc.project.umark.domain.report.ReportType;
import umc.project.umark.domain.report.dto.Request.ReportRequest;
import umc.project.umark.domain.report.dto.Response.ReportResponse;

import java.time.LocalDateTime;
@Slf4j
public class ReportConverter {
    public static Report toReport(ReportRequest.ReportRequestDTO request){ //report 생성

        ReportType reportType = null;
        switch (request.getReport()){
            case 1:
                reportType = ReportType.REPORT_TYPE1;
                break;
            case 2:
                reportType = ReportType.REPORT_TYPE2;
                break;
            case 3:
                reportType = ReportType.REPORT_TYPE3;
                break;
            case 4:
                reportType = ReportType.REPORT_TYPE4;
                break;
        }
        log.info("request get reason: {}",request.getReason());

        return Report.builder()
                .reportType(reportType)
                .reason((reportType == ReportType.REPORT_TYPE4) ? request.getReason() : null)
                .build();

    }
    public static ReportResponse.ReportResponseDTO toReportCreateResponseDTO(BookMark bookmark){ //response dto 생성

        return  ReportResponse.ReportResponseDTO.builder()
                .bookMarkId(bookmark.getId())
                .reportCount(bookmark.getReportCount())
                .isReported(bookmark.isReported())
                .reportCreatedAt(LocalDateTime.now())
                .build();

    }
}
