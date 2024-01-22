package umc.project.umark.domain.member;

public class MemberConverter {

    public static MemberResponse.MemberRegistDto memberRegistDto(String email, String univName, String password){
        return MemberResponse.MemberRegistDto.builder()
                .email(email)
                .univName(univName)
                .password(password)
                .build();
    }

    public static MemberResponse.MemberResponseDto memberResponseDto(String email, String password){
        return MemberResponse.MemberResponseDto.builder()
                .email(email)
                .password(password)
                .memberStatus(MemberStatus.ACTIVE.toString())
                .build();
    }
}
