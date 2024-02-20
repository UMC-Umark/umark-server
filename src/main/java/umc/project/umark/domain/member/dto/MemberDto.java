package umc.project.umark.domain.member.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

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
        private List<Integer> terms;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailCheckDto{
        private String email;
        private String univName;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeCheckDto{
        private String email;
        private String univName;
        private String code;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDto{
        private String email;
        private String password;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponseDto{
        private Long memberId;
        private String accessToken;
        private String refreshToken;
        private String role;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberResponseDto{
        private Long id;
        private String email;
        private String univ;
        private String password;
        private String memberStatus;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberSignUpResponseDto{
        private Long id;
        private String email;
        private String univ;
        private String password;
        private String memberStatus;
        private List<Long> agreedTerms;
    }


    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberFindPasswordDto{
        private String email;
        private String newPassword;
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReissueRequestDto{
        private String refreshToken;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReissueResponseDto{
        private Long memberId;
        private String accessToken;
        private String refreshToken;
    }

}
