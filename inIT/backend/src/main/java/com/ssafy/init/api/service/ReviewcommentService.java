package com.ssafy.init.api.service;

import com.ssafy.init.api.request.ReviewcommentReq;
import com.ssafy.init.api.response.ReviewcommentRes;

import java.util.List;

public interface ReviewcommentService {
    boolean insert(ReviewcommentReq boardCommentReq);

    boolean delete(int commentId, int userId);

    List<ReviewcommentRes> getFilteringInfo(int boardId);
}
