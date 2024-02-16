package umc.project.umark.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.project.umark.domain.term.entity.Term;
import umc.project.umark.global.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String univ;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    @Enumerated(EnumType.STRING)
    private MemberRole role;
    @Column
    @Builder.Default
    private Integer writtenCount = 0;
    @Column
    @Builder.Default
    private Integer likedCount = 0;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "user_terms",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "termId")
    )
    private Set<Term> agreedTerms = new HashSet<>();

    public void increaseWrittenCount() {
        this.writtenCount++;
    }

    public void increaseLikedCount() {
        this.likedCount++;
    }

    public void decreaseWrittenCount() {
        if (this.writtenCount > 0) {
            this.writtenCount--;
        }
    }

    public void decreaseLikedCount() {
        if (this.likedCount > 0) {
            this.likedCount--;
        }
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void withdraw() {
        this.memberStatus = MemberStatus.WITHDRAWN;
    }
}
