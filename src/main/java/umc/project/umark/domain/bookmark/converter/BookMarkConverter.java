package umc.project.umark.domain.bookmark.converter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
public class BookMarkConverter {

    public static BookMarkResponse.BookMarkCreateResponseDTO toBookMarkCreateResponseDTO(BookMark bookMark){ //response dto 생성

        return BookMarkResponse.BookMarkCreateResponseDTO.builder()
                .BookMarkId(bookMark.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static BookMark toBookMark(BookMarkRequest.BookMarkCreateRequestDTO request){ //request로 받은 dto를 바탕으로 bookmark 생성

       // content 내용을 로그로 출력
        log.info("Content received in request: {}", request.getContent());
        return BookMark.builder()
                .title(request.getTitle())
                .url(request.getUrl())
                .content(request.getContent())
                .bookMarkHashTags(new ArrayList<>())
                .build();
    }
}
