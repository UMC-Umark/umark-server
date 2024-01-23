package umc.project.umark.domain.member;

public class MemberConverter {

    public static MemberApiResponse.MemberSignUpDto memberSignUpDto(String email, String univName, String password){
        return MemberApiResponse.MemberSignUpDto.builder()
                .email(email)
                .univName(univName)
                .password(password)
                .build();
    }

    public static MemberApiResponse.MemberResponseDto memberResponseDto(String email, String password){
        return MemberApiResponse.MemberResponseDto.builder()
                .email(email)
                .password(password)
                .memberStatus(MemberStatus.ACTIVE.toString())
                .build();
    }
}
