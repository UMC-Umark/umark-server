package umc.project.umark.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import umc.project.umark.domain.bookmark.BookMark;
import umc.project.umark.global.common.BaseEntity;
import umc.project.umark.member.Member;
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted_at is null")
public class BookMarkLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private BookMark bookmark;

    public void setMember(Member member){
        this.member = member;
    }
    public void setBookMark(BookMark bookmark){
        this.bookmark = bookmark;
        bookmark.getBookMarkLikes().add(this);
    }

}
