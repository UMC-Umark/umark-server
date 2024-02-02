package umc.project.umark.domain.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.hashtag.service.HashTagService;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.report.Report;
import umc.project.umark.domain.report.dto.Request.ReportRequest;

public interface BookMarkService {
    BookMark createBookMark(BookMarkRequest.BookMarkCreateRequestDTO request);
    BookMark likeBookMark(Long memberId, Long bookMarkId);
    Long deleteBookMark(Long memberId, Long bookMarkId);
    Report createReport(ReportRequest.ReportRequestDTO request);

}