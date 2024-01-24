package umc.project.umark.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.bookmark.repository.BookMarkRepository;
import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.hashtag.repository.HashTagRepository;
import umc.project.umark.domain.hashtag.service.HashTagService;
import umc.project.umark.domain.mapping.BookMarkHashTag;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.mapping.converter.BookMarkHashTagConverter;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;
//import umc.project.umark.domain.member.repository.MemberRepository;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookMarkServiceImpl implements BookMarkService{

    private  final BookMarkRepository bookMarkRepository;
    private final HashTagRepository hashTagRepository;
    private final HashTagService hashTagService;
    @Override
    @Transactional
    public BookMark createBookMark(BookMarkRequest.BookMarkCreateRequestDTO request) {
        BookMark newBookMark = BookMarkConverter.toBookMark(request); // dto를 엔티티로 바꾼거

        List<HashTag> hashTagList = request.getHashTags().stream()
                .map(hashTag -> {
                    return hashTagRepository.findByContent(hashTag.getContent()).orElseGet(() -> hashTagService.createHashTag(hashTag)); //없으면 해쉬태그 만들어서 반환
                }).collect(Collectors.toList());

        List <BookMarkHashTag> bookMarkHashTagList = BookMarkHashTagConverter.toBookMarkHashTagList(hashTagList); //request에 있는 것들로 bookmarkhashtag 만들기
        bookMarkHashTagList.forEach(bookMarkHashTag -> bookMarkHashTag.setBookMark(newBookMark));
        return bookMarkRepository.save(newBookMark);

    }

    @Override
    @Transactional
    public BookMark LikeBookMark(Long memberId, Long bookMarkId) {
        //memberRepository.findById(memberId);

        //게시물이 존재하는지 검증
        BookMark bookmark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

        // likeCount 증가
        bookmark.increaseLikeCount();

       // BookMarkLike newBookMarkLike = BookMarkLikeConverter(member);
        //newBookMarkLike.setBookMark(bookmark);

        return bookmark;

    }


}
