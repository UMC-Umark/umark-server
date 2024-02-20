package umc.project.umark.domain.member.email.repository;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.project.umark.domain.member.email.entity.EmailCode;

import java.util.Optional;

@Repository
public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {
    EmailCode findByCode(String code);
}
