package umc.project.umark.domain.bookmark.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
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
import umc.project.umark.global.exception.GlobalException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/BookMarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping("/add")
    public ApiResponse<BookMarkResponse.BookMarkCreateResponseDTO> addBookMark(@RequestBody @Valid BookMarkRequest.BookMarkCreateRequestDTO request){
        BookMark newBookMark = bookMarkService.createBookMark(request);
        return ApiResponse.onSuccess(BookMarkConverter.toBookMarkCreateResponseDTO(newBookMark));
    }

    @PostMapping("/likes")
    public ApiResponse<BookMarkLikeResponse.BookMarkLikeResponseDTO> BookMarkLike(@RequestParam Long bookMarkId, @RequestParam Long memberId) {

            BookMark bookMark = bookMarkService.likeBookMark(memberId, bookMarkId);
            return ApiResponse.onSuccess(BookMarkLikeConverter.toBookMarkLikeResponseDTO(bookMark));

    }

   @DeleteMapping("/delete")
   public ApiResponse<BookMarkResponse.BookMarkDeleteResponseDTO> deleteBookMark(@RequestParam Long bookMarkId,@RequestParam Long memberId){
        Long deletedBookMarkId = bookMarkService.deleteBookMark(memberId,bookMarkId);
       return ApiResponse.onSuccess(BookMarkConverter.toBookMarkDeleteResponseDTO(deletedBookMarkId));
   }

    @PostMapping("/reports")
    public ApiResponse<ReportResponse.ReportResponseDTO> createReport(@RequestBody ReportRequest.ReportRequestDTO request) {

        Report newReport = bookMarkService.createReport(request);
        return ApiResponse.onSuccess(ReportConverter.toReportCreateResponseDTO(newReport));

    }

}
