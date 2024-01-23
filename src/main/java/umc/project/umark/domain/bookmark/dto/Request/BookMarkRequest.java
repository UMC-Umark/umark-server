package umc.project.umark.domain.bookmark.dto.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import umc.project.umark.domain.hashtag.entity.HashTag;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;

import java.util.List;


public class BookMarkRequest {
    @Getter
    public static class BookMarkCreateRequestDTO{

        @NotBlank
        private String title;

        private String url;

        private String content;

        private List<HashTag> hashTags;

    }


}
