package umc.project.umark.domain.member.auth.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.project.umark.domain.member.entity.Member;
import umc.project.umark.domain.member.repository.MemberRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberUtils {
    private final MemberRepository memberRepository;

    public Long getCurrentMemberId() {
        return SecurityUtils.getCurrentMemberId();
    }

    public Optional<Member> getCurrentMember() {
        return memberRepository.findById(getCurrentMemberId());
    }
}
