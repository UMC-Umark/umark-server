package umc.project.umark.domain.bookmark.converter;

import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;

import java.time.LocalDateTime;


public class BookMarkConverter {
    public static BookMarkResponse.BookMarkCreateResponseDTO toBookMarkCreateResponseDTO(BookMark bookMark){
        return BookMarkResponse.BookMarkCreateResponseDTO.builder()
                .BookMarkId(bookMark.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static BookMark toBookMark(BookMarkRequest.BookMarkCreateRequestDTO request){

        return BookMark.builder()
                .title(request.getTitle())
                .url(request.getUrl())
                .content(request.getContent())
                .hashTags(request.getHashTags())
                .build();
    }
}
