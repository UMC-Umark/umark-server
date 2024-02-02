package umc.project.umark.domain.mapping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.domain.member.entity.Member;

import java.util.Optional;
@Repository
public interface BookMarkLikeRepository  extends JpaRepository<BookMarkLike, Long> {
    Page<BookMarkLike> findAllByMember(Member member, PageRequest pageRequest);
    Optional<BookMarkLike> findByMemberAndBookmark(Member member, BookMark bookmark);
}
