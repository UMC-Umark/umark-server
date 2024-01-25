package umc.project.umark.domain.term.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.global.common.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Term extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean isAgree = false;

    @Column(nullable = false)
    private Boolean isCrucial;

    public void setCrucial(Boolean crucial) {
        isCrucial = crucial;
    }

    public void agree() {
        this.isAgree = true;
    }
}
