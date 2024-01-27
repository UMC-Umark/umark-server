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
import umc.project.umark.global.common.ApiResponse;
import umc.project.umark.global.exception.GlobalException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/BookMarks")
public class BookMarkController {
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkService bookMarkService;

    @PostMapping("/add")
    public ApiResponse<BookMarkResponse.BookMarkCreateResponseDTO> addBookMark(@RequestBody @Valid BookMarkRequest.BookMarkCreateRequestDTO request){
        BookMark newBookMark = bookMarkService.createBookMark(request);
        return ApiResponse.onSuccess(BookMarkConverter.toBookMarkCreateResponseDTO(newBookMark));
    }

    @PostMapping("/likes")
    public ApiResponse<BookMarkResponse.BookMarkLikeResponseDTO> BookMarkLike(@RequestParam Long bookMarkId, @RequestParam Long memberId) {

            BookMark bookMark = bookMarkService.likeBookMark(memberId, bookMarkId);
            return ApiResponse.onSuccess(BookMarkConverter.toBookMarkLikeResponseDTO(bookMark));

    }

//    @DeleteMapping("/BookMarks")
//    public ApiResponse<BookMarkResponse.BookMarkDeleteResponseDTO> deleteBookMark(@RequestParam Long bookMarkId,@RequestParam Long memberId){
//
//        //return ApiResponse.onSuccess(BookMarkConverter.toBookMarkDeleteResponseDTO(member));
//    }

}
