package umc.project.umark.domain.member;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import umc.project.umark.global.exception.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.project.umark.global.exception.GlobalException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/sendemail")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response =  new HashMap<>();
        try{
            Boolean result = memberService.sendEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName());
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/checkemail")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try{
            Boolean result = memberService.checkEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName(), memberSignUpDto.getCode());
            response.put("success", result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
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
