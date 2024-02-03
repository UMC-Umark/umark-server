package umc.project.umark.domain.term.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.term.converter.TermConverter;
import umc.project.umark.domain.term.dto.TermDto;
import umc.project.umark.domain.term.entity.Term;
import umc.project.umark.domain.term.repository.TermRepository;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TermServiceImpl implements TermService{
    private final TermRepository termRepository;
    private final TermConverter termConverter;

    @Override
    @Transactional
    public void createTerm(TermDto.TermRequestDto requestDto) {
        Term newTerm =  termConverter.toTerm(requestDto.getTitle(),
                requestDto.getDescription(), requestDto.getIsCrucial());

        termRepository.save(newTerm);

        log.info("New Term created with id: {}", newTerm.getId());
    }
    @Override
    @Transactional
    public List<TermDto.TermResponseDto> getTerms() {
        List<Term> terms = termRepository.findAll();
        return termConverter.toGetTerms(terms);
    }

}
