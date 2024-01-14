package umc.project.umark.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
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
    private String likeCount;

    @Column(nullable = false)
    private String reportCount;

    @OneToMany(mappedBy = "bookmark")
    private List<HashTag> hashTags;

    @OneToMany(mappedBy = "bookmark")
    private List<HashTag> Report;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn
    //private Member member;
}
