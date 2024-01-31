package umc.project.umark.domain.mapping.converter;

import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.mapping.dto.BookMarkLikeResponse;
import umc.project.umark.domain.member.entity.Member;

import java.time.LocalDateTime;


public class BookMarkLikeConverter {
    public static BookMarkLike toBookMarkLike(Member member){
        return BookMarkLike.builder()
                .member(member)
                .build();
    }
    public static BookMarkLikeResponse.BookMarkLikeResponseDTO toBookMarkLikeResponseDTO(BookMark bookMark){

        return BookMarkLikeResponse.BookMarkLikeResponseDTO.builder()
                .bookMarkId(bookMark.getId())
                .likeCount(bookMark.getLikeCount())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
