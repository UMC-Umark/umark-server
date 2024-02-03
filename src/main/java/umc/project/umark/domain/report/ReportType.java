package umc.project.umark.domain.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReportType {

    REPORT_TYPE1("스팸/홍보/도배글이에요."),
    REPORT_TYPE2("허위 정보를 기재했어요."),
    REPORT_TYPE3("부적절한 내용입니다."),
    REPORT_TYPE4("기타(직접 작성하기)".trim());

    private final String description;
}
