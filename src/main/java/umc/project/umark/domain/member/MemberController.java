package umc.project.umark.domain.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import umc.project.umark.global.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.project.umark.global.exception.GlobalException;

import java.io.IOException;

@RestController
@RequestMapping("api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/sendemail")
    public String sendEmail(@RequestBody MemberResponse.MemberRegistDto memberRegistDto) throws IOException {
        memberService.sendEmail(memberRegistDto.getEmail(), memberRegistDto.getUnivName());
        return "test";
    }

    @PostMapping("/checkemail")
    public String checkEmail(@RequestBody MemberResponse.MemberRegistDto memberRegistDto) throws IOException {
        memberService.checkEmail(memberRegistDto.getEmail(), memberRegistDto.getUnivName(), memberRegistDto.getCode());
        return "test";
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse.MemberResponseDto> signUpMember(@RequestBody MemberResponse.MemberRegistDto memberRegistDto) {
        try{
            String email = memberRegistDto.getEmail();
            String password = memberRegistDto.getPassword();

            memberService.signUpMember(email, password);

            return ApiResponse.SuccessResponse("성공", MemberConverter.memberResponseDto(email, password));
        } catch (GlobalException e){
            return ApiResponse.ErrorResponse(e.getErrorCode());
        }
    }

}
