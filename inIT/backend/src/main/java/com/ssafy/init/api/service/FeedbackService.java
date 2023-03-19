package com.ssafy.init.api.service;

import com.ssafy.init.api.request.FeedbackReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.api.response.FeedbackRes;
import com.ssafy.init.db.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    int insert(FeedbackReq feedbackReq);

    int modify(FeedbackReq feedbackReq);

    int delete(int feedbackId, int userId);

    FeedbackRes getInfo(int feedbackId);

    List<FeedbackRes> getAllInfo();

    boolean changeCommentCnt(int feedbackId, int commentCnt);

    Page<FeedbackRes> pageList(Pageable pageable);
}
