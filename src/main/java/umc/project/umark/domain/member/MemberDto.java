package umc.project.umark.domain.member;

import lombok.*;

public class MemberDto {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSignUpDto{
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
