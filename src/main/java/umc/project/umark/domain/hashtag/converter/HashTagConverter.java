package umc.project.umark.domain.hashtag.converter;

import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.hashtag.entity.HashTag;

import java.time.LocalDateTime;

public class HashTagConverter {

    public static HashTag toHashTag(HashTag hashTag){    //인자로 받은 해쉬태그로 해쉬태그 엔티티 생성
        return HashTag.builder()
                .content(hashTag.getContent())
                .build();
    }


}
