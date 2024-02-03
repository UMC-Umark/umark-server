package umc.project.umark.domain.bookmark.dto.Response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookMarkUpdateResponse {
    Long BookMarkId;
    LocalDateTime modifiedAt;
}

