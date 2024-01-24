package umc.project.umark.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.project.umark.global.common.BaseEntity;

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
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;

    private Integer writtenCount = 0;

    private Integer likedCount = 0;

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
}
