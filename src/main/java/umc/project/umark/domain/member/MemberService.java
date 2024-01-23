package umc.project.umark.domain.member;

import com.univcert.api.UnivCert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class MemberService {

    @Value("${univcert.apikey}")
    private String apiKey;

    @Autowired
    private MemberRepository memberRepository;

    public Boolean sendEmail (String email, String univName) throws IOException {
        try {
            Map<String, Object> result = UnivCert.certify(apiKey, email, univName, true);
            log.info("메일 전송");

            if (result.get("success").equals(true)) {
                return true;
            }

            else{
                throw new GlobalException(GlobalErrorCode.WRONG_EMAIL_FORM);
            }

        } catch (Exception e) {
            e.getMessage();
        }

        return false;
    }



    public Boolean checkEmail (String email, String univName, int code) throws IOException {
        try {
            Map<String, Object> result = UnivCert.certifyCode(apiKey, email, univName, code);
            log.info("메일 전송");

            if (result.get("success").equals(true)) {
                return true;
            }

            else{
                throw new GlobalException(GlobalErrorCode.WRONG_EMAIL_FORM);
            }
        } catch (Exception e) {
        e.getMessage();
        }

        return false;
    }

    @Transactional
    public Member signUpMember (String email, String password) throws GlobalException {

        Optional <Member> findmember = memberRepository.findByEmail(email);

        if (findmember.isPresent()){
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

}