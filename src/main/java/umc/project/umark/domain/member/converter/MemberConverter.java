package umc.project.umark.domain.member.converter;

import org.springframework.stereotype.Component;
import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.domain.term.entity.Term;
import umc.project.umark.domain.member.entity.MemberStatus;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
                .id(member.getId())
                .email(member.getEmail())
                .univ(member.getUniv())
                .password(member.getPassword())
                .memberStatus(String.valueOf(member.getMemberStatus()))
                .build();
    }

    public static MemberDto.MemberSignUpResponseDto memberSignUpResponseDto(Member member){
        List<Long> agreedTermsIds = member.getAgreedTerms().stream()
                .map(Term::getId)
                .collect(Collectors.toList());
        return MemberDto.MemberSignUpResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .univ(member.getUniv())
                .password(member.getPassword())
                .memberStatus(String.valueOf(member.getMemberStatus()))
                .agreedTerms(agreedTermsIds)
                .build();
    }

    public static MemberDto.LoginResponseDto toLogin(Member member, String accessToken, String refreshToken){
        return MemberDto.LoginResponseDto.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(String.valueOf(member.getRole()))
                .build();
    }
}
