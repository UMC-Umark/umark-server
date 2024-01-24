package umc.project.umark.domain.mapping.converter;

import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.mapping.BookMarkHashTag;

import java.util.List;
import java.util.stream.Collectors;

public class BookMarkHashTagConverter {

    public static List<BookMarkHashTag> toBookMarkHashTagList(List <HashTag> hashTagList){
        return hashTagList.stream()
                .map(hashTag -> BookMarkHashTag.builder()
                        .hashtag(hashTag)
                        .build()
                ).collect(Collectors.toList());
    }

}
