package com.ssafy.init.api.service;

import com.ssafy.init.api.request.FeedbackcommentReq;
import com.ssafy.init.api.response.FeedbackcommentRes;

import java.util.List;

public interface FeedbackcommentService {
    boolean insert(FeedbackcommentReq feedbackcommentReq);

    boolean delete(int feedbackId, int userId);

    List<FeedbackcommentRes> getFilteringInfo(int feedbackId);

    boolean pick(int commentId, int userId);

    boolean unpick(int commentId, int userId);
}
