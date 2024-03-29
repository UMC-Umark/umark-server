package umc.project.umark.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.bookmark.entity.BookMark;
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
    @Setter
    private BookMark bookmark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private HashTag hashtag;

    public void addBookMark(BookMark bookmark){
        this.bookmark = bookmark;
        bookmark.getBookMarkHashTags().add(this);
    }
}
