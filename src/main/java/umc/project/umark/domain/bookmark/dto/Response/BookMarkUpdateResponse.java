package umc.project.umark.domain.bookmark.dto.Response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BookMarkUpdateResponse {
    private Long BookMarkId;
    private String title;
    private List<String> hashTagContent;
    private String content;
    private String url;
    private LocalDateTime modifiedAt;
}

