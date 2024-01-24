package umc.project.umark.domain.member;

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
    public ApiResponse<Map<String, Object>> sendEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response =  new HashMap<>();
        try{
            Boolean result = memberService.sendEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName());
            response.put("success", result);
            return new ApiResponse<>(true, "200", "성공하였습니다", response);
        } catch (IOException e) {
            response.put("error", e);
            return new ApiResponse<>(false, "400", e.getMessage().toString(), response);
        }
    }

    @PostMapping("/checkemail")
    public ApiResponse<Map<String, Object>> checkEmail(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try{
            Boolean result = memberService.checkEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName(), memberSignUpDto.getCode());
            response.put("success", result);
            return new ApiResponse<>(true, "200", "성공하였습니다", response);
        } catch (IOException e){
            response.put("error", e);
            return new ApiResponse<>(false, "400", e.getMessage().toString(), response);
        }
    }

    @PostMapping("/signup")
    public ApiResponse<MemberApiResponse.MemberResponseDto> signUpMember(@RequestBody MemberApiResponse.MemberSignUpDto memberSignUpDto) {
        String email = memberSignUpDto.getEmail();
        String password = memberSignUpDto.getPassword();
        try{
            memberService.signUpMember(email, password);
            return ApiResponse.onSuccess(MemberConverter.memberResponseDto(email, password));
        } catch (GlobalException e){
            return ApiResponse.onFailure(e.getErrorCode(), MemberConverter.memberResponseDto(email, password));
        }
    }

}
