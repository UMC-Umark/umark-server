package umc.project.umark.domain.hashtag.converter;

import umc.project.umark.domain.bookmark.dto.Response.BookMarkResponse;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.hashtag.entity.HashTag;

import java.time.LocalDateTime;

public class HashTagConverter {

    public static HashTag toHashTag(HashTag hashTag){
        return HashTag.builder()
                .content(hashTag.getContent())
                .build();
    }


}
