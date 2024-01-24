package umc.project.umark.domain.member.converter;

import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.entity.MemberStatus;

public class MemberConverter {

    public static MemberDto.MemberSignUpDto memberSignUpDto(String email, String univName, String password){
        return MemberDto.MemberSignUpDto.builder()
                .email(email)
                .univName(univName)
                .password(password)
                .build();
    }

    public static MemberDto.MemberResponseDto memberResponseDto(String email, String password){
        return MemberDto.MemberResponseDto.builder()
                .email(email)
                .password(password)
                .memberStatus(MemberStatus.ACTIVE.toString())
                .build();
    }
}
