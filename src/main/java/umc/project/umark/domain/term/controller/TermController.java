package umc.project.umark.domain.term.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umc.project.umark.domain.term.dto.TermDto;
import umc.project.umark.domain.term.service.TermService;
import umc.project.umark.global.common.ApiResponse;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class TermController {

    private final TermService termService;

    @PostMapping("/term/add")
    public ApiResponse addTerm(
            @RequestBody TermDto.TermRequestDto requestDto
    ) {
        termService.createTerm(requestDto);
        return ApiResponse.onSuccess("");
    }

    @GetMapping("/terms")
    public ApiResponse<List<TermDto.TermResponseDto>> getTerms() {
        return ApiResponse.onSuccess(termService.getTerms());
    }

}
