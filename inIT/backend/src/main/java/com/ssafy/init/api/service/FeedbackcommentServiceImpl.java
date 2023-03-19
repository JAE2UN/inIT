package com.ssafy.init.api.service;

import com.ssafy.init.api.request.FeedbackcommentReq;
import com.ssafy.init.api.response.FeedbackcommentRes;
import com.ssafy.init.db.entity.Boardcomment;
import com.ssafy.init.db.entity.Feedback;
import com.ssafy.init.db.entity.Feedbackcomment;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.FeedbackRepository;
import com.ssafy.init.db.repository.FeedbackcommentRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackcommentServiceImpl implements FeedbackcommentService {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackcommentRepository feedbackcommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public boolean insert(FeedbackcommentReq feedbackcommentReq) {
        int userId = feedbackcommentReq.getUserId();
        int feedbackId = feedbackcommentReq.getFeedbackId();

        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<User> user = userRepository.findById(userId);
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (user.isPresent() && feedback.isPresent()) {
            try {
                int cnt = feedback.get().getComment_cnt() + 1;

                Feedbackcomment feedbackcomment = new Feedbackcomment();

                feedbackcomment.setContent(feedbackcommentReq.getContent());
                feedbackcomment.setUser(user.get());
                feedbackcomment.setFeedback(feedback.get());

                feedbackcommentRepository.save(feedbackcomment);
                feedbackService.changeCommentCnt(feedbackId, cnt);
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 피드백 댓글 저장 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 피드백 댓글 저장 전역 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    @Transactional
    public boolean delete(int commentId, int userId) {
        if (commentId < 0 || commentId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<Feedbackcomment> feedbackcomment = feedbackcommentRepository.findById(commentId);
        if (feedbackcomment.isPresent()) {
            if (feedbackcomment.get().getUser().getId() != userId) return (false);

            try {
                int feedbackId = feedbackcomment.get().getFeedback().getId();
                int cnt = feedbackcomment.get().getFeedback().getComment_cnt() - 1;

                feedbackcommentRepository.delete(feedbackcomment.get());
                feedbackService.changeCommentCnt(feedbackId, cnt);
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 피드백 댓글 삭제 중 처리 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 피드백 댓글 수정 중 전역 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public List<FeedbackcommentRes> getFilteringInfo(int feedbackId) {
        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE) return (null);

        List<Feedbackcomment> feedbackcommentList = feedbackcommentRepository.findAllByFeedbackId(feedbackId);
        List<FeedbackcommentRes> feedbackcommentResList = new ArrayList<>();

        for (int i = 0; i < feedbackcommentList.size(); i++) {
            FeedbackcommentRes feedbackcommentRes = new FeedbackcommentRes();

            feedbackcommentRes.setUserNick(feedbackcommentList.get(i).getUser().getNick());
            feedbackcommentRes.setUserGrade(feedbackcommentList.get(i).getUser().getGrade());
            feedbackcommentRes.setUserTemperature(feedbackcommentList.get(i).getUser().getTemperature());

            feedbackcommentRes.setId(feedbackcommentList.get(i).getId());
            feedbackcommentRes.setContent(feedbackcommentList.get(i).getContent());
            
            // [smj] dateTime -> date (String)
            String onlyDate = feedbackcommentList.get(i).getCreatedAt().toString().substring(2, 10);
            feedbackcommentRes.setCreateTime(onlyDate);
//            feedbackcommentRes.setCreateTime(feedbackcommentList.get(i).getCreatedAt());
            
            feedbackcommentRes.setPick(feedbackcommentList.get(i).isPick());

            feedbackcommentResList.add(feedbackcommentRes);
        }
        return (feedbackcommentResList);
    }

    private boolean changePickStatus(int commentId, boolean flag) {
        Optional<Feedbackcomment> feedbackcomment = feedbackcommentRepository.findById(commentId);
        if (feedbackcomment.isPresent()) {
            try {
                feedbackcomment.get().setPick(flag);
                feedbackcommentRepository.save(feedbackcomment.get());
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 피드백 댓글 좋아요 처리(On/Off) 중 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 피드백 댓글 좋아요 처리(On/Off) 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean pick(int commentId, int userId) {
        if (commentId < 0 || commentId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<Feedbackcomment> feedbackcomment = feedbackcommentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && feedbackcomment.isPresent()) {
            int feedbackUserId = feedbackcomment.get().getFeedback().getUser().getId();

            if (feedbackUserId != userId) return (false);
            changePickStatus(commentId, true);
            return (true);
        }
        return (false);
    }

    @Override
    public boolean unpick(int commentId, int userId) {
        if (commentId < 0 || commentId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<Feedbackcomment> feedbackcomment = feedbackcommentRepository.findById(commentId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent() && feedbackcomment.isPresent()) {
            int feedbackUserId = feedbackcomment.get().getFeedback().getUser().getId();

            if (feedbackUserId != userId) return (false);
            changePickStatus(commentId, false);
            return (true);
        }
        return (false);
    }
}
