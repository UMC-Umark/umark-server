package umc.project.umark.domain.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.project.umark.domain.bookmark.entity.BookMark;
@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    
}
