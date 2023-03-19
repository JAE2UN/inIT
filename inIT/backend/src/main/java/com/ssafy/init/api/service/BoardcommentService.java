package com.ssafy.init.api.service;

import com.ssafy.init.api.request.BoardcommentReq;
import com.ssafy.init.api.response.BoardcommentRes;

import java.util.List;

public interface BoardcommentService {
    boolean insert(BoardcommentReq boardCommentReq);

    boolean delete(int commentId, int userId);

    List<BoardcommentRes> getFilteringInfo(int boardId);
}
