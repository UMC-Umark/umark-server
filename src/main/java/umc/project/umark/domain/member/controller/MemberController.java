package umc.project.umark.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.project.umark.domain.member.converter.MemberConverter;
import umc.project.umark.domain.member.dto.MemberDto;
import umc.project.umark.domain.member.service.MemberService;
import umc.project.umark.global.common.ApiResponse;
import umc.project.umark.global.exception.GlobalException;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sendemail")
    public ApiResponse<Map<String, Object>> sendEmail(@RequestBody MemberDto.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean result = memberService.sendEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName());
            response.put("success", result);
            return new ApiResponse<>(true, "200", "성공하였습니다", response);
        } catch (IOException e) {
            response.put("error", e);
            return new ApiResponse<>(false, "400", e.getMessage().toString(), response);
        }
    }

    @PostMapping("/checkemail")
    public ApiResponse<Map<String, Object>> checkEmail(@RequestBody MemberDto.MemberSignUpDto memberSignUpDto) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            Boolean result = memberService.checkEmail(memberSignUpDto.getEmail(), memberSignUpDto.getUnivName(), memberSignUpDto.getCode());
            response.put("success", result);
            return new ApiResponse<>(true, "200", "성공하였습니다", response);
        } catch (IOException e) {
            response.put("error", e);
            return new ApiResponse<>(false, "400", e.getMessage().toString(), response);
        }
    }

    @PostMapping("/signup")
    public ApiResponse<MemberDto.MemberSignUpResponseDto> signUpMember(@RequestBody MemberDto.MemberSignUpDto memberSignUpDto) {
        String email = memberSignUpDto.getEmail();
        String password = memberSignUpDto.getPassword();
        String univ = memberSignUpDto.getUnivName();
        List<Integer> term = memberSignUpDto.getTerms();
        try {
            return ApiResponse.onSuccess(MemberConverter.memberSignUpResponseDto(memberService.signUpMember(email, password, univ, term)));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), MemberConverter.memberSignUpResponseDto(memberService.signUpMember(email, password, univ, term)));
        }
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberDto.MemberResponseDto> getMember(@PathVariable Long memberId) {
        try {
            return ApiResponse.onSuccess(memberService.getMember(memberId));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @GetMapping("/all")
    public ApiResponse<List<MemberDto.MemberResponseDto>> getAllMembers() {
        try {
            return ApiResponse.onSuccess(memberService.getAllMembers());
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    /*
    @PostMapping("/sendpasswordmail")
    public ApiResponse<String> snedFindPasswordMail(@RequestBody MemberDto.MemberFindPasswordDto findPasswordDto) {
        try {
            return ApiResponse.onSuccess(memberService.sendFindPasswordMail(findPasswordDto.getEmail()));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }
     */

    @PatchMapping("/changepasswordbyemail")
    public ApiResponse<MemberDto.MemberResponseDto> changePasswordByEmail(@RequestBody MemberDto.MemberFindPasswordDto findPasswordDto) {
        try {
            return ApiResponse.onSuccess(MemberConverter.memberResponseDto(memberService.changePasswordByEmail(findPasswordDto.getEmail(), findPasswordDto.getNewPassword())));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @PatchMapping("/changepassword/{memberId}")
    public ApiResponse<MemberDto.MemberResponseDto> changePassword(@RequestBody MemberDto.MemberFindPasswordDto findPasswordDto, @PathVariable Long memberId) {
        try {
            return ApiResponse.onSuccess(MemberConverter.memberResponseDto(memberService.changePassword(memberId, findPasswordDto.getNewPassword())));
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

    @PatchMapping("/{memberId}")
    public ApiResponse witdraw(
            @PathVariable Long memberId
    ) {
        try {
            memberService.withdraw(memberId);
            return ApiResponse.onSuccess(null);
        } catch (GlobalException e) {
            return ApiResponse.onFailure(e.getErrorCode(), null);
        }
    }

}
