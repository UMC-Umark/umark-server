package umc.project.umark.domain.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkInquiryResponse;
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

    Page<BookMarkInquiryResponse> inquiryBookMarkPage(Integer page);//모든 북마크 조회
    Page<BookMarkInquiryResponse> inquiryBookMarkByLikeCount(Integer page); // 추천 북마크 조회
    Page<BookMarkInquiryResponse> inquiryBookMarkByMemberLike(Long memberId, Integer page); // 좋아요 한 북마크 조회
    Page<BookMarkInquiryResponse> inquiryBookMarkByMember(Long memberId, Integer page); // 내가 쓴 북마크 조회
    Page<BookMarkInquiryResponse> inquiryBookMarkBySearch(String keyWord, Integer page); // 북마크 검색

    //BookMarkUpdateResponse updateBookMark(Long bookMarkId, BookMarkRequest.BookMarkUpdateRequest requset);
}