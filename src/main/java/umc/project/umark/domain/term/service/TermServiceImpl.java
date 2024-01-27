//package umc.project.umark.domain.term.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import umc.project.umark.domain.term.dto.TermDto;
//import umc.project.umark.domain.term.entity.Term;
//import umc.project.umark.domain.term.repository.TermRepository;
//import umc.project.umark.global.exception.GlobalErrorCode;
//import umc.project.umark.global.exception.GlobalException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class TermServiceImpl implements TermService{
//    private final TermRepository termRepository;
//
////    @Override
////    @Transactional
////    public void createTerm(TermDto.TermRequestDto requestDto) {
////        if (requestDto.getIsAgree() == null) {
////            throw new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND);
////        };
////        Term newTerm =  Term.builder()
////                .description(requestDto.getDescription())
////                .isCrucial(requestDto.getIsCrucial())
////                .build();
////
////        termRepository.save(newTerm);
////
////        log.info("New Term created with id: {}", newTerm.getId());
////    }
//}
