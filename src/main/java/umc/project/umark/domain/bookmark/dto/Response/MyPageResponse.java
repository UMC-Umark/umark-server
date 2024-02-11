package umc.project.umark.domain.bookmark.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import umc.project.umark.domain.bookmark.entity.BookMark;


public class MyPageResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageLikedBookMarkResponse {
        private Page<BookMarkInquiryResponse> myLikeBookMarkPage;
        private Integer writtenCount;
        private  Integer LikedCount;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyPageWrittenBookMarkResponse {
        private Page<BookMarkInquiryResponse> myWrittenBookMarkPage;
        private Integer writtenCount;
        private  Integer LikedCount;

    }
}
