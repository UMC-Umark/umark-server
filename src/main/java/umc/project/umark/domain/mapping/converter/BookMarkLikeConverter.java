package umc.project.umark.domain.mapping.converter;

import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.member.Member;

public class BookMarkLikeConverter {
    public static BookMarkLike toBookMarkLike(Member member, BookMark bookMark){
        return BookMarkLike.builder()
                .member(member)
                .bookmark(bookMark)
                .build();
    }
}
