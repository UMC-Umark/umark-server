package umc.project.umark.domain.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.member.entity.Member;

import java.time.LocalDateTime;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Page<BookMark> findAll(Pageable pageable);

    @Query("SELECT b FROM BookMark b WHERE b.createdAt >= :weekAgo AND b.likeCount > 9 ORDER BY b.likeCount DESC")
    Page<BookMark> findAllByOrderByLikeCount(Pageable pageable, @Param("weekAgo") LocalDateTime weekAgo);
    Page<BookMark> findAllByMember(Member member, Pageable pageable);

    @Query("SELECT DISTINCT  b FROM BookMark b JOIN b.bookMarkHashTags t WHERE t.hashtag.content LIKE %:keyword% OR b.content LIKE %:keyword% OR b.title LIKE %:keyword%")
    Page<BookMark> findAllBySearch(String keyword, Pageable pageable);

    @Query("SELECT DISTINCT b FROM BookMark b JOIN b.bookMarkHashTags t WHERE b.likeCount > 9 AND (t.hashtag.content LIKE %:keyword% OR b.content LIKE %:keyword% OR b.title LIKE %:keyword%)")
    Page<BookMark> findAllByLikeCountAndSearch(String keyword, Pageable pageable);

    boolean existsByIdAndMember_Id(Long bookMarkId, Long memberId);
}
