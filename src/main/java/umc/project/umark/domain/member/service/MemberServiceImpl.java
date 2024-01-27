package umc.project.umark.domain.member.service;

import com.univcert.api.UnivCert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.member.converter.MemberConverter;
import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.repository.MemberRepository;
import umc.project.umark.domain.member.entity.MemberStatus;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Value("${univcert.apikey}")
    private String apiKey;

    @Autowired
    private MemberRepository memberRepository;

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
    public Member signUpMember(String email, String password) throws GlobalException {

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

}
