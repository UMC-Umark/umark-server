package umc.project.umark.domain.member.service;

import com.univcert.api.UnivCert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.member.converter.MemberConverter;
import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.repository.MemberRepository;
import umc.project.umark.domain.member.entity.MemberStatus;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Value("${univcert.apikey}")
    private String apiKey;

    //@Value("${spring.mail.username}")
    //private String sender;

    private final MemberRepository memberRepository;
    // private final JavaMailSender javaMailSender;

    @Override
    public Boolean sendEmail(String email, String univName) throws IOException {
        try {
            Map<String, Object> result = UnivCert.certify(apiKey, email, univName, true);
            log.info("메일 전송 : {}", "메일 " + email + " 대학 " + univName);

            if (result.get("success").equals(true)) {
                return true;
            } else{
                throw new IOException((String)result.get("message"));
            }

        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public Boolean checkEmail(String email, String univName, int code) throws IOException {
        try {
            Map<String, Object> result = UnivCert.certifyCode(apiKey, email, univName, code);
            log.info("메일 인증 : {}", "메일 " + email + " 대학 " + univName + " 코드 " + code);

            if (result.get("success").equals(true)) {
                UnivCert.clear(apiKey, email);
                return true;
            }

            else{
                throw new IOException((String)result.get("message"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public Member signUpMember(String email, String password) {

        Optional <Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
        }

        else{
            Member member = Member.builder()
                    .email(email)
                    .password(password)
                    .memberStatus(MemberStatus.ACTIVE)
                    .build();

            memberRepository.save(member);

            return member;
        }
    }

    @Override
    public MemberDto.MemberResponseDto getMember(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            MemberDto.MemberResponseDto memberResponseDto= MemberConverter.memberResponseDto(member);
            return memberResponseDto;
        }

        else{
            throw new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @Override
    public List<MemberDto.MemberResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto.MemberResponseDto> memberResponseDtos = new ArrayList<>();

        for (Member member : members) {
            MemberDto.MemberResponseDto memberResponseDto = MemberConverter.memberResponseDto(member);
            memberResponseDtos.add(memberResponseDto);
        }

        return memberResponseDtos;
    }

    /*
    @Override
    public String makeRandomCode() {

        //8자리 코드 생성

        String codeSet = "ABCDEFGHIJKMNLOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder randomCode = new StringBuilder();

        for (int i=0; i<8; i++) {
            int index = random.nextInt(codeSet.length());
            randomCode.append(codeSet.charAt(index));
        }

        return String.valueOf(randomCode);
    }

    @Override
    public String sendFindPasswordMail(String email) {

        String code = makeRandomCode();

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(email);
        mail.setFrom(sender);
        mail.setSubject("비밀번호 찾기 코드 메일");
        mail.setText(code);

        try {
            javaMailSender.send(mail);
        } catch (Exception e) {
            throw e;
        }

        return code;
    }

     */

    @Override
    @Transactional
    public Member changePasswordByEmail(String email, String newPassword) {
        Optional <Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.changePassword(newPassword);
            memberRepository.save(member);
            return member;
        } else {
            throw new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public Member changePassword(Long memberId, String newPassword) {
        Optional <Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.changePassword(newPassword);
            memberRepository.save(member);
            return member;
        } else {
            throw new GlobalException(GlobalErrorCode.INVALID_CODE);
        }

    }

    @Override
    @Transactional
    public void withdraw(Long memberId) {
        Optional <Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.withdraw();
        } else {
            throw new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }
    }

}
