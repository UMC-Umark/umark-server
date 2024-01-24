package umc.project.umark.domain.term.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.project.umark.domain.term.entity.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
}
