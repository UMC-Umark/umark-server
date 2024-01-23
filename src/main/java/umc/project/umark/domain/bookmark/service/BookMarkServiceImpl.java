package umc.project.umark.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.bookmark.repository.BookMarkRepository;
import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.hashtag.repository.HashTagRepository;
import umc.project.umark.domain.hashtag.service.HashTagService;
import umc.project.umark.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookMarkServiceImpl implements BookMarkService{

    private  final BookMarkRepository bookMarkRepository;
    private final MemberRepository memberRepository;
    private final HashTagRepository hashTagRepository;
    private final HashTagService hashTagService;
    @Override
    public BookMark createBookMark(BookMarkRequest.BookMarkCreateRequestDTO request) {
        BookMark newBookMark = BookMarkConverter.toBookMark(request);

        List<HashTag> hashTagList = request.getHashTags().stream()
                .map(hashTag -> {
                    return hashTagRepository.findByContent(hashTag.getContent()).orElseGet(() -> hashTagService.createHashTag(hashTag)); //없으면 해쉬태그 만듬
                }).collect(Collectors.toList());
        return newBookMark;
        //request에 있는 것들로 bookmarkhashtag 만들기
    }


}
