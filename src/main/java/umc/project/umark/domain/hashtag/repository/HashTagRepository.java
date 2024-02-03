package umc.project.umark.domain.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.project.umark.domain.hashtag.entity.HashTag;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByContent(String content);
}
