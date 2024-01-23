package umc.project.umark.domain.bookmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import umc.project.umark.domain.bookmark.dto.Request.BookMarkRequest;
import umc.project.umark.domain.bookmark.entity.BookMark;
import umc.project.umark.domain.hashtag.service.HashTagService;

public interface BookMarkService {
    BookMark createBookMark(BookMarkRequest.BookMarkCreateRequestDTO request);
    BookMark LikeBookMark(Long memberId, Long bookMarkId);

}