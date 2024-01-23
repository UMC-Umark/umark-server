package umc.project.umark.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.project.umark.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
