package umc.project.umark.domain.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.project.umark.domain.bookmark.entity.BookMark;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    
}
