package umc.project.umark.domain.member.converter;

import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.domain.member.entity.MemberStatus;

public class MemberConverter {

    public static MemberDto.MemberSignUpDto memberSignUpDto(String email, String univName, String password){
        return MemberDto.MemberSignUpDto.builder()
                .email(email)
                .univName(univName)
                .password(password)
                .build();
    }

    public static MemberDto.MemberResponseDto memberResponseDto(Member member){
        return MemberDto.MemberResponseDto.builder()
                .email(member.getEmail())
                .univ(member.getUniv())
                .password(member.getPassword())
                .memberStatus(String.valueOf(member.getMemberStatus()))
                .build();
    }
}
