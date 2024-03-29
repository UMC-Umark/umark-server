package umc.project.umark.domain.bookmark.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkInquiryResponse;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkUpdateResponse;
import umc.project.umark.domain.bookmark.dto.Response.MyPageResponse;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.bookmark.repository.BookMarkRepository;
import umc.project.umark.domain.bookmark.service.BookMarkService;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.mapping.converter.BookMarkLikeConverter;
import umc.project.umark.domain.mapping.dto.BookMarkLikeResponse;
import umc.project.umark.domain.report.Report;
import umc.project.umark.domain.report.converter.ReportConverter;
import umc.project.umark.domain.report.dto.Request.ReportRequest;
import umc.project.umark.domain.report.dto.Response.ReportResponse;
import umc.project.umark.global.common.ApiResponse;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping("/add")
    public ApiResponse<BookMarkResponse.BookMarkCreateResponseDTO> addBookMark(@RequestBody @Valid BookMarkRequest.BookMarkCreateRequestDTO request){
        BookMark newBookMark = bookMarkService.createBookMark(request);
        return ApiResponse.onSuccess(BookMarkConverter.toBookMarkCreateResponseDTO(newBookMark));
    }

    @PostMapping("/{bookMarkId}/likes")
    public ApiResponse<BookMarkLikeResponse.BookMarkLikeResponseDTO> bookMarkLike(
            @PathVariable Long bookMarkId
            ) {

        BookMark bookMark = bookMarkService.likeBookMark(bookMarkId);
        return ApiResponse.onSuccess(BookMarkLikeConverter.toBookMarkLikeResponseDTO(bookMark));
    }


    @DeleteMapping("/delete/{bookMarkId}")
    public ApiResponse<BookMarkResponse.BookMarkDeleteResponseDTO> deleteBookMark(
            @PathVariable Long bookMarkId) {

        Long deletedBookMarkId = bookMarkService.deleteBookMark(bookMarkId);
        return ApiResponse.onSuccess(BookMarkConverter.toBookMarkDeleteResponseDTO(deletedBookMarkId));
    }


    @PostMapping("/reports")
    public ApiResponse<ReportResponse.ReportResponseDTO> createReport(@RequestBody ReportRequest.ReportRequestDTO request) {

       BookMark bookmark = bookMarkService.createReport(request);
        return ApiResponse.onSuccess(ReportConverter.toReportCreateResponseDTO(bookmark));

    }

    @GetMapping("") // 모든 북마크 조회
    public ApiResponse<Page<BookMarkInquiryResponse>> inquiryBookMark(@RequestParam(name = "page") Integer page) {
        try {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkPage(page));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @GetMapping("/recommends") // 추천 북마크 조회
    public ApiResponse<Page<BookMarkInquiryResponse>> inquiryBookMarkByLikeCount(@RequestParam(name = "page") Integer page){
        try {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkByLikeCount(page));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }
    @GetMapping("/mywrite") // 내가 쓴 북마크 조회
    public ApiResponse<MyPageResponse.MyPageWrittenBookMarkResponse> inquiryBookMarkByMember(
            @RequestParam(name = "page") Integer page
    ) {
        try {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkByMember(page));
        } catch (GlobalException e) {
            return  ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @GetMapping("/mylike") // 내가 좋아요한 북마크 조회
    public ApiResponse<MyPageResponse.MyPageLikedBookMarkResponse> inquiryBookMarkByMemberLike(
            @RequestParam(name = "page") Integer page
    ) {
        try {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkByMemberLike(page));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @GetMapping("/search") //모든 북마크 검색
    public ApiResponse<Page<BookMarkInquiryResponse>> inquiryBookMarkBySearch(
            @RequestParam(name = "page") Integer page,
            @RequestParam String keyWord
    ) {
        if (keyWord == null) {
            throw new GlobalException(GlobalErrorCode.NOT_VALID_KEYWORD);
        } else {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkBySearch(keyWord, page));
        }
    }
    @GetMapping("/recommend/search") //추천 북마크 검색
    public ApiResponse<Page<BookMarkInquiryResponse>> inquiryBookMarkByLikeCountAndSearch(
            @RequestParam(name = "page") Integer page,
            @RequestParam String keyWord
    ) {
        if (keyWord == null) {
            throw new GlobalException(GlobalErrorCode.NOT_VALID_KEYWORD);
        } else {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkByLikeCountAndSearch(keyWord, page));
        }
    }

    @GetMapping("/update/{bookmarkId}")//수정할 게시물 정보 조회
    public ApiResponse<BookMarkInquiryResponse> inquiryBookMarkById(@PathVariable Long bookmarkId){
        try {
            return ApiResponse.onSuccess(bookMarkService.inquiryBookMarkById(bookmarkId));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }


    @PutMapping("/{bookmarkId}")//북마크 수정
    public ApiResponse<BookMarkUpdateResponse> updateBookMark(@PathVariable Long bookmarkId, @RequestBody BookMarkRequest.BookMarkUpdateRequest request){
        return ApiResponse.onSuccess(bookMarkService.updateBookMark(bookmarkId, request));
    }

}
