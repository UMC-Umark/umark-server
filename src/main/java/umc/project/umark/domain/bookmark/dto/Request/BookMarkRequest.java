package umc.project.umark.domain.bookmark.dto.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import umc.project.umark.domain.hashtag.entity.HashTag;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;

import java.util.List;


public class BookMarkRequest {
    @Getter
    public static class BookMarkCreateRequestDTO{

        @Size(max = 20)
        private String title;

        private String url;

        @Size(max = 250)
        private String content;

        private List<HashTag> hashTags;

    }

    @Getter
    public static class BookMarkUpdateRequest{

        @Size(max = 20)
        private String title;

        @Size(max = 20)
        private String url;

        @Size(max = 250)
        private String content;

        private List<HashTag> hashTags;

    }


}
