package umc.project.umark.domain.hashtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.project.umark.domain.hashtag.converter.HashTagConverter;
import umc.project.umark.domain.hashtag.entity.HashTag;
import umc.project.umark.domain.hashtag.repository.HashTagRepository;
@Service
@RequiredArgsConstructor
@Slf4j
public class HashTagServiceImpl implements HashTagService{
    private final HashTagRepository hashTagRepository;
    @Override
    @Transactional
    public HashTag createHashTag(HashTag hashTag){
            HashTag newHashTag = HashTagConverter.toHashTag(hashTag);
            return hashTagRepository.save(newHashTag);
    }

}
