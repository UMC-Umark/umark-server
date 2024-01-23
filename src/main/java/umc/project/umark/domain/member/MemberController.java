package umc.project.umark.domain.member;

import org.springframework.http.ResponseEntity;
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
    public String sendEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        memberService.sendEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName());
        return "test";
    }

    @PostMapping("/checkemail")
    public String checkEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        memberService.checkEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName(), memberSignUpDto.getCode());
        return "test";
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberApiResponse.MemberResponseDto> signUpMember(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) {
        try{
            String email = memberSignUpDto.getEmail();
            String password = memberSignUpDto.getPassword();

            memberService.signUpMember(email, password);

            return ApiResponse.SuccessResponse("성공", MemberConverter.memberResponseDto(email, password));
        } catch (GlobalException e){
            return ApiResponse.ErrorResponse(e.getErrorCode());
        }
    }

}
