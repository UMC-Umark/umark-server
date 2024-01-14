package umc.project.umark.domain.report;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ReportType {

    REPORT_TYPE1("스팸/홍보/도배글이에요."),
    REPORT_TYPE2("허위 정보를 기재했어요."),
    REPORT_TYPE3("부적절한 내용입니다.");

    private final String description;
}
