package umc.project.umark.domain.term.converter;

import org.springframework.stereotype.Component;
import umc.project.umark.domain.term.dto.TermDto;
import umc.project.umark.domain.term.entity.Term;

import java.util.List;

@Component
public class TermConverter {

    public Term toTerm(String title, String description, Boolean isCrucial) {
        return Term.builder()
                .title(title)
                .description(description)
                .isCrucial(isCrucial)
                .build();
    }

    public TermDto.TermResponseDto toTermResponseDto(Term term) {
        return TermDto.TermResponseDto.builder()
                .id(term.getId())
                .title(term.getTitle())
                .description(term.getDescription())
                .isCrucial(term.getIsCrucial())
                .build();
    }

    public List<TermDto.TermResponseDto> toGetTerms(List<Term> terms) {
        return terms.stream().map(this::toTermResponseDto).toList();
    }
}
