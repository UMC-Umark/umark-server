package umc.project.umark.domain.member.service;

import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.entity.Member;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    public Boolean sendEmail(String email, String univName) throws IOException;

    public Boolean checkEmail(String email, String univName, int code) throws  IOException;

    public Member signUpMember(String email, String password, String univ, List<Integer> terms);

    public MemberDto.LoginResponseDto login(MemberDto.LoginRequestDto request);
    public MemberDto.MemberResponseDto getMember(Long memberId);
    public List<MemberDto.MemberResponseDto> getAllMembers();
    // public String makeRandomCode();
    // public String sendFindPasswordMail(String email);
    public Member changePasswordByEmail(String email, String newPassword);
    public Member changePassword(Long memberId, String newPassword);

    public MemberDto.ReissueResponseDto reissue(MemberDto.ReissueRequestDto refreshToken);

    public void withdraw();
}
