package umc.project.umark.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import umc.project.umark.domain.HashTag.HashTag;
import umc.project.umark.domain.bookmark.BookMark;
import umc.project.umark.global.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted_at is null")
public class BookMarkHashTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private BookMark bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private HashTag hashtag;

    public void setBookMark(BookMark bookmark){
        this.bookmark = bookmark;
        bookmark.getBookMarkHashTags().add(this);
    }

    public void setHashTag(HashTag hashtag){
        this.hashtag = hashtag;
    }
}
