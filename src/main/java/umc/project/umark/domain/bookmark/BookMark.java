package umc.project.umark.domain.bookmark;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import umc.project.umark.domain.HashTag.HashTag;
import umc.project.umark.domain.mapping.BookMarkHashTag;
import umc.project.umark.domain.mapping.BookMarkLike;
import umc.project.umark.member.Member;
import umc.project.umark.domain.report.Report;
import umc.project.umark.global.common.BaseEntity;

import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "deleted_at is null")
public class BookMark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String url;

    @Column(nullable = true)
    private String content;

    @Column(nullable = false)
    private Integer likeCount=0;

    @Column(nullable = false)
    private Integer reportCount=0;

    @OneToMany(mappedBy = "bookmark")
    private List<HashTag> hashTags;

    @OneToMany(mappedBy = "bookmark",cascade = CascadeType.ALL)
    private List<Report> reports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "bookmark",cascade = CascadeType.ALL)
    private List<BookMarkLike> bookMarkLikes;

    @OneToMany(mappedBy = "bookmark",cascade = CascadeType.ALL)
    private List<BookMarkHashTag> bookMarkHashTags;


    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void increaseReportCount() {
        this.reportCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void decreaseReportCount() {
        if (this.reportCount > 0) {
            this.reportCount--;
        }
    }

}
