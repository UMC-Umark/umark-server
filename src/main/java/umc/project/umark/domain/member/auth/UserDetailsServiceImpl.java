package umc.project.umark.domain.member.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.project.umark.domain.member.repository.MemberRepository;
import umc.project.umark.global.exception.GlobalErrorCode;
import umc.project.umark.global.exception.GlobalException;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return memberRepository
                .findByEmail(email)
                .map(member -> User.builder()
                        .username(String.valueOf(member.getId()))
                        .password(passwordEncoder.encode(member.getPassword())) // 데이터베이스에 저장된 인코딩된 비밀번호 사용
                        .roles(String.valueOf(member.getRole())) // 'ROLE_' 접두사가 포함된 권한 값 사용
                        .build())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_INFO_NOT_FOUND));
    }
}
