package umc.project.umark.domain.term.service;

import umc.project.umark.domain.term.dto.TermDto;
import umc.project.umark.domain.term.entity.Term;

import java.util.List;

public interface TermService {
    public void createTerm(TermDto.TermRequestDto requestDto);
    public List<TermDto.TermResponseDto> getTerms();
}
