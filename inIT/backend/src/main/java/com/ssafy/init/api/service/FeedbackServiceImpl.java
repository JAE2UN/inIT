package com.ssafy.init.api.service;

import com.ssafy.init.api.request.FeedbackReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.api.response.FeedbackRes;
import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Feedback;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.FeedbackRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebrtcService webrtcService;

    @Override
    public int insert(FeedbackReq feedbackReq) {
        int userId = feedbackReq.getUserId();

        if (userId < 0 || userId > Integer.MAX_VALUE) return (-1);
        try {
            Feedback feedback = new Feedback();
            User user = new User();
            int resultId = -1;

            feedback.setTitle(feedbackReq.getTitle());
            feedback.setContent(feedbackReq.getContent());
            feedback.setVideo_url(webrtcService.getUrl(feedbackReq.getReportId(), feedbackReq.getSequence()));

            user.setId(feedbackReq.getUserId());
            feedback.setUser(user);

            resultId = feedbackRepository.save(feedback).getId();

            return (resultId);
        } catch (IllegalArgumentException iae) {
            System.out.println(">>> 피드백 게시판 글 작성 중 저장 에러");
            return (-1);
        } catch (Exception e) {
            System.out.println(">>> 피드백 게시판 글 작성 중 전역 에러");
            return (-1);
        }
    }

    //수정 - Update : 글 수정 시 권한 없는 요청(-1), 수정 실패(0) / 성공(1) 여부를 반환
    @Override
    public int modify(FeedbackReq feedbackReq) {
        int feedbackId = feedbackReq.getId();

        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE) return (-1);

        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) {
            if (feedbackReq.getUserId() != feedback.get().getUser().getId()) return (-1);

            if (feedbackReq.getTitle() != null) feedback.get().setTitle(feedbackReq.getTitle());
            if (feedbackReq.getContent() != null) feedback.get().setContent(feedbackReq.getContent());

            try {
                feedbackRepository.save(feedback.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 피드백 게시판 글 수정 중 저장 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 피드백 게시판 글 수정 중 전역 에러");
                return (0);
            }
        }
        return (-1);
    }

    //삭제 - Delete : 글 삭제 시 권한 없는 요청(-1), 삭제 실패(0) / 성공(1) 여부를 반환
    @Override
    public int delete(int feedbackId, int userId) {
        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if(feedback.isPresent()) {
            if (feedback.get().getUser().getId() != userId) return (-1);

            try {
                feedbackRepository.delete(feedback.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 피드백 게시판 글 삭제 중 처리 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 피드백 게시판 글 수정 중 전역 에러");
                return (0);
            }
        }
        return (-1);
    }

    private FeedbackRes convertFeedbackRes(Feedback feedback) {
        Optional<User> user = userRepository.findById(feedback.getUser().getId());
        FeedbackRes feedbackRes = new FeedbackRes();

        if (user.isPresent()) {
            feedbackRes.setUserNick(user.get().getNick());
            feedbackRes.setUserGrade(user.get().getGrade());
            feedbackRes.setUserTemperature(user.get().getTemperature());

            feedbackRes.setId(feedback.getId());
            
            // [smj] dateTime -> date (String)
            String onlyDate = feedback.getCreatedAt().toString().substring(2, 10);
            feedbackRes.setCreateTime(onlyDate);
//            feedbackRes.setCreateTime(feedback.getCreatedAt());
            
            feedbackRes.setUpdateTime(feedback.getUpdatedAt());
            feedbackRes.setTitle(feedback.getTitle());
            feedbackRes.setContent(feedback.getContent());
            feedbackRes.setCommentCnt(feedback.getComment_cnt());
            feedbackRes.setVideoUrl(feedback.getVideo_url());

            return (feedbackRes);
        }
        return (null);
    }

    @Override
    public FeedbackRes getInfo(int feedbackId) {
        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE) return (null);

        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) return (convertFeedbackRes(feedback.get()));

        return (null);
    }

    @Override
    public List<FeedbackRes> getAllInfo() {
        List<Feedback> feedbackList = feedbackRepository.findAllByOrderByIdDesc();
        List<FeedbackRes> feedbackResList = new ArrayList<>();

        for (int i = 0; i < feedbackList.size(); i++) {
            FeedbackRes feedbackRes = convertFeedbackRes(feedbackList.get(i));
            if (feedbackRes != null) feedbackResList.add(feedbackRes);
        }
        return (feedbackResList);
    }

    @Override
    public boolean changeCommentCnt(int feedbackId, int commentCnt) {
        if (feedbackId < 0 || feedbackId > Integer.MAX_VALUE) return (false);

        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) {
            feedback.get().setComment_cnt(commentCnt);

            try {
                feedbackRepository.save(feedback.get());
            } catch (Exception e) {
                System.out.println(">>> 피드백 게시판 댓글 개수 수정 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public Page<FeedbackRes> pageList(Pageable pageable) {
        return (feedbackRepository.findAll(pageable).map(feedback -> { return (convertFeedbackRes(feedback)); } ));
    }
}
