package umc.project.umark.domain.bookmark.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.project.umark.domain.bookmark.converter.BookMarkConverter;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkInquiryResponse;
import umc.project.umark.domain.bookmark.dto.Response.BookMarkUpdateResponse;
import umc.project.umark.domain.bookmark.dto.Response.MyPageResponse;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.bookmark.repository.BookMarkRepository;
import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.hashtag.repository.HashTagRepository;
import umc.project.umark.domain.hashtag.service.HashTagService;
import umc.project.umark.domain.mapping.BookMarkHashTag;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.mapping.converter.BookMarkHashTagConverter;
import umc.project.umark.domain.mapping.repository.BookMarkLikeRepository;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.domain.report.Report;
import umc.project.umark.domain.report.converter.ReportConverter;
import umc.project.umark.domain.report.dto.Request.ReportRequest;
import umc.project.umark.domain.report.repository.ReportRepository;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;
import umc.project.umark.domain.member.repository.MemberRepository;
import umc.project.umark.domain.mapping.converter.BookMarkLikeConverter;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookMarkServiceImpl implements BookMarkService{

    private final BookMarkRepository bookMarkRepository;
    private final MemberRepository memberRepository;
    private final HashTagRepository hashTagRepository;
    private final HashTagService hashTagService;
    private final BookMarkLikeRepository bookMarkLikeRepository;
    private final ReportRepository reportRepository;
    private final BookMarkConverter bookMarkConverter;
    @Override
    @Transactional
    public BookMark createBookMark(BookMarkRequest.BookMarkCreateRequestDTO request) {  //북마크 생성
        Long memberId = request.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        BookMark newBookMark = BookMarkConverter.toBookMark(request,member); // dto를 엔티티로 바꾼거

        List<HashTag> hashTagList = request.getHashTags().stream()
                .map(hashTag -> {
                    return hashTagRepository.findByContent(hashTag.getContent()).orElseGet(() -> hashTagService.createHashTag(hashTag)); //없으면 해쉬태그 만들어서 반환
                }).collect(Collectors.toList());

        List <BookMarkHashTag> bookMarkHashTagList = BookMarkHashTagConverter.toBookMarkHashTagList(hashTagList); //request에 있는 것들로 bookmarkhashtag 만들기
        bookMarkHashTagList.forEach(bookMarkHashTag -> bookMarkHashTag.setBookMark(newBookMark));

        member.increaseWrittenCount();
        return bookMarkRepository.save(newBookMark);

    }

    @Override
    @Transactional
    public BookMark likeBookMark(Long memberId, Long bookMarkId) {  //북마크에 좋아요 누르기

        //멤버가 존재하는지 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        //게시물이 존재하는지 검증
        BookMark bookmark = bookMarkRepository.findById(bookMarkId)
                    .orElseThrow(() -> new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

        //이미 멤버가 해당 북마크에 좋아요를 눌렀는 지 확인
        Optional<BookMarkLike> existingLike = bookMarkLikeRepository.findByMemberAndBookmark(member, bookmark);
        //이미 좋아요를 누른 상태라면
        if(existingLike.isPresent()){
            BookMarkLike bookMarkLike = existingLike.get();
            bookmark.decreaseLikeCount();  // 좋아요 수 감소
            member.decreaseLikedCount();   //내가 좋아요 한 수 감소
            bookMarkLikeRepository.deleteById(bookMarkLike.getId());  // BookMarkLike 엔터티 삭제

        }

        else{
        // likeCount 증가
        bookmark.increaseLikeCount();
        member.increaseLikedCount();
        //BookMarkLike에 멤버, 북마크 객체 주입
         BookMarkLike newBookMarkLike = BookMarkLikeConverter.toBookMarkLike(member);
         newBookMarkLike.setBookMark(bookmark);
        }

        return bookmark;

    }

   @Override
   @Transactional
   public Long deleteBookMark(Long memberId, Long bookMarkId){
       //멤버가 존재하는지 검증
       Member member = memberRepository.findById(memberId)
               .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

       //게시물이 존재하는지 검증
       BookMark bookmark = bookMarkRepository.findById(bookMarkId)
               .orElseThrow(() -> new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

       if (!bookmark.getMember().equals(member)) {
           //만약 게시물의 작성자와 삭제하고자 하는 멤버의 id가 다르다면
           throw new GlobalException(GlobalErrorCode.MEMBER_NOT_AUTHORIZED);
       }

       Long deletedBookMarkId = bookmark.getId();         //삭제할 북마크의 아이디 저장
       bookMarkRepository.deleteById(bookmark.getId());   //북마크 삭제 cascade.all타입이므로 관련 mapping table은 자동 삭제
       member.decreaseWrittenCount();
       return deletedBookMarkId;

   }

    @Override
    @Transactional
    public BookMark createReport(ReportRequest.ReportRequestDTO request){    //북마크에 신고 누르기
        Long memberId = request.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Long bookMarkId = request.getBookMarkId();

        BookMark bookmark = bookMarkRepository.findById(bookMarkId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

        Report newReport = ReportConverter.toReport(request);
        newReport.setBookMark(bookmark);
        bookmark.increaseReportCount();
        if(bookmark.getReportCount()>=10){          //신고 누적 수가 10이상이면 북마크 상태가 신고됨으로 바뀜.
           bookmark.setReported(true);
        }
        reportRepository.save(newReport);
        return bookmark;
    }

    @Override // 모든 북마크 조회
    @Transactional
    public Page<BookMarkInquiryResponse> inquiryBookMarkPage(Integer page){
        Page <BookMark> bookMarkPage = bookMarkRepository.findAll(PageRequest.of(page-1,15, Sort.by("createdAt").descending()));

        return bookMarkPage.map(bookMarkConverter::toBookMarkInquiryResponse);
    }

    @Override // 추천 북마크 조회
    @Transactional
    public Page<BookMarkInquiryResponse> inquiryBookMarkByLikeCount(Integer page){
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        Page<BookMark> bookMarkPage = bookMarkRepository.findAllByOrderByLikeCount(PageRequest.of(page-1,15), weekAgo);

        return bookMarkPage.map(bookMarkConverter::toBookMarkInquiryResponse);
    }

    @Override //내가 좋아요 누른 북마크 조회
    @Transactional
    public MyPageResponse.MyPageLikedBookMarkResponse inquiryBookMarkByMemberLike(Long memberId, Integer page){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        Page <BookMarkInquiryResponse> bookMarkPage = bookMarkLikeRepository.findAllByMember(member, PageRequest.of(page-1,12))
                .map(BookMarkLike::getBookmark)
                .map(bookMarkConverter::toBookMarkInquiryResponse);

        return bookMarkConverter.toMyPageLikedBookMarkResponse(bookMarkPage, member);
    }

    @Override //내가 쓴 북마크 조회
    @Transactional
    public MyPageResponse.MyPageWrittenBookMarkResponse inquiryBookMarkByMember(Long memberId, Integer page){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));;
        Page <BookMarkInquiryResponse> bookMarkPage = bookMarkRepository.findAllByMember(member, PageRequest.of(page-1,12))
                .map(bookMarkConverter::toBookMarkInquiryResponse);

        return bookMarkConverter.toMyPageWrittenBookMarkResponse(bookMarkPage, member);
    }

    @Override //모든 북마크 검색
    @Transactional
    public Page<BookMarkInquiryResponse> inquiryBookMarkBySearch(String keyWord, Integer page){
        Page <BookMark> bookMarkPage = bookMarkRepository.findAllBySearch(keyWord, PageRequest.of(page-1, 15));

        return bookMarkPage.map(bookMarkConverter::toBookMarkInquiryResponse);
    }

    @Override //추천 북마크 검색
    @Transactional
    public Page<BookMarkInquiryResponse> inquiryBookMarkByLikeCountAndSearch(String keyword, Integer page){
        Page <BookMark> bookMarkPage = bookMarkRepository.findAllByLikeCountAndSearch(keyword, PageRequest.of(page-1, 15));

        return bookMarkPage.map(bookMarkConverter::toBookMarkInquiryResponse);
    }

    @Override
    @Transactional
    public  BookMarkInquiryResponse inquiryBookMarkById(Long bookMarkId){
        BookMark bookMark = bookMarkRepository.findById(bookMarkId).orElseThrow(() ->  new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

        return bookMarkConverter.toBookMarkInquiryResponse(bookMark);
    }

    @Override//북마크 수정
    @Transactional
    public BookMarkUpdateResponse updateBookMark(Long bookMarkId, BookMarkRequest.BookMarkUpdateRequest request){
        BookMark bookMark = bookMarkRepository.findById(bookMarkId).orElseThrow(() -> new GlobalException(GlobalErrorCode.BOOKMARK_NOT_FOUND));

        List<HashTag> hashTagList = request.getHashTags().stream()
                .map(hashTag -> hashTagRepository.findByContent(hashTag.getContent())
                        .orElseGet(() -> hashTagService.createHashTag(hashTag)))
                .collect(Collectors.toList());
        List <BookMarkHashTag> bookMarkHashTagList = BookMarkHashTagConverter.toBookMarkHashTagList(hashTagList);

        bookMark.update(request.getTitle(), request.getUrl(), request.getContent(), bookMarkHashTagList);
        return bookMarkConverter.toBookMarkUpdateResponse(bookMarkRepository.save(bookMark));
    }

}
