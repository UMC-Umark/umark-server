package umc.project.umark.domain.member.service;

import com.univcert.api.UnivCert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.member.auth.utils.MemberUtils;
import umc.project.umark.domain.member.converter.MemberConverter;
import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.entity.MemberRole;
import umc.project.umark.domain.member.repository.MemberRepository;
import umc.project.umark.domain.member.entity.MemberStatus;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.domain.term.entity.Term;
import umc.project.umark.domain.term.repository.TermRepository;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;
import org.springframework.security.core.GrantedAuthority;
import umc.project.umark.global.jwt.JwtTokenService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Value("${univcert.apikey}")
    private String apiKey;
    private final MemberRepository memberRepository;
    private final TermRepository termRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenService jwtTokenService;
    private final MemberConverter memberConverter;
    private final MemberUtils memberUtils;

    @Override
    public Boolean sendEmail(String email, String univName) throws IOException {
            Map<String, Object> result = UnivCert.certify(apiKey, email, univName, true);
            log.info("result" + result);
            log.info("메일 전송 : {}", "메일 " + email + " 대학 " + univName);

            if (result.get("success").equals(true)) {
                return true;
            } else{
                throw new IOException((String)result.get("message"));
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
    public Member signUpMember(String email, String password, String univ, List<Integer> terms) {

        Optional <Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(email)
                .univ(univ)
                .password(password)
                .memberStatus(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build();

        Set<Term> agreedTerms = terms.stream()
                .map(termId -> termRepository.findById(Long.valueOf(termId)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        member.getAgreedTerms().addAll(agreedTerms);

        // 회원 저장
        memberRepository.save(member);

        return member;
    }

    @Override
    @Transactional
    public MemberDto.LoginResponseDto login(MemberDto.LoginRequestDto request) {
        String email = request.getEmail();
        String password = request.getPassword();

        memberRepository.findByEmail(email); // UserId Notfound 예외처리용

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        System.out.println("authenticationToken = " + authenticationToken);
        System.out.println("object = " + authenticationManagerBuilder.getObject());
////
//        try {
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//            System.out.println("authentication = " + authentication);
//        } catch (AuthenticationException e) {
//            System.out.println("Authentication failed: " + e.getMessage());
//            // 여기서 적절한 예외 처리 또는 로직을 수행합니다.
//        }

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String role =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")); // role_admin
        String authenticatedUserId = authentication.getName(); //  UserId

        Member member = memberRepository.findById(Long.valueOf(authenticatedUserId))
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_INFO_NOT_FOUND)); // id
        String accessToken = jwtTokenService.generateAccessToken(member.getId(), role);
        String refreshToken = jwtTokenService.generateRefreshToken(member.getId());

        return memberConverter.toLogin(member, accessToken, refreshToken);
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
    public void withdraw() {
        Long memberId = memberUtils.getCurrentMemberId();
        Optional <Member> findMember = memberRepository.findById(memberId);

        if (findMember.isPresent()) {
            Member member = findMember.get();
            member.withdraw();
        } else {
            throw new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND);
        }
    }

}
