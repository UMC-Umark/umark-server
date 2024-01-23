package umc.project.umark.domain.bookmark.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.bookmark.repository.BookMarkRepository;
import umc.project.umark.domain.bookmark.service.BookMarkService;
import umc.project.umark.global.common.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/BookMarks")
public class BookMarkController {
    private final BookMarkRepository bookMarkRepository;
    private final BookMarkService bookMarkService;

    @PostMapping("/add")
    public BaseResponse<BookMarkResponse.BookMarkCreateResponseDTO> addBookMark(@RequestBody @Valid BookMarkRequest.BookMarkCreateRequestDTO request){
        BookMark newBookMark = bookMarkService.createBookMark(request);
        return BaseResponse.onSuccess(BookMarkConverter.toBookMarkCreateResponseDTO(newBookMark));
    }


}
