package umc.project.umark.domain.member;

import lombok.*;

public class MemberResponse {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberRegistDto{
        private String email;
        private String univName;
        private String password;
        private int code;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponseDto{
        private String email;
        private String password;
        private String memberStatus;
    }

}
