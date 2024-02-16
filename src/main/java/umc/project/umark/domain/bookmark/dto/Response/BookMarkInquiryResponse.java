package umc.project.umark.domain.bookmark.dto.Response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BookMarkInquiryResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private List<String> hashTagContent;
    private String content;
    private String url;
    private Integer likeCount;
    private boolean isReported;
}
