package umc.project.umark.domain.member.service;

import umc.project.umark.domain.member.entity.Member;

import java.io.IOException;

public interface MemberService {

    public Boolean sendEmail(String email, String univName) throws IOException;

    public Boolean checkEmail(String email, String univName, int code) throws  IOException;

    public Member signUpMember(String email, String password);
}
