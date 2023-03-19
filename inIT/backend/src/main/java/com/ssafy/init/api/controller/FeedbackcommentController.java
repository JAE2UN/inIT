package com.ssafy.init.api.controller;


import com.ssafy.init.api.request.BoardcommentReq;
import com.ssafy.init.api.request.FeedbackcommentReq;
import com.ssafy.init.api.response.BoardcommentRes;
import com.ssafy.init.api.response.FeedbackcommentRes;
import com.ssafy.init.api.service.FeedbackcommentService;
import com.ssafy.init.db.entity.Feedbackcomment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//[psh]
@Api("피드백 게시판 댓글 API")
@RestController
@RequestMapping("/feedbackcomment")
public class FeedbackcommentController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    //Create Delete FindAll

    @Autowired
    private FeedbackcommentService feedbackcommentService;

    @ApiOperation(value="댓글 작성", notes = "댓글을 작성하고 결과(success: 작성 성공, fail: 작성 실패)를 반환", response = String.class)
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody FeedbackcommentReq feedbackcommentReq) {
        logger.debug("[ 피드백 게시판 댓글 작성 호출 ]");
        System.out.println(">>> FeedbackcommentController write : " + feedbackcommentReq);

        boolean result = feedbackcommentService.insert(feedbackcommentReq);

        if (result) {
            System.out.println(">>> 피드백 게시글 댓글 입력 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 피드백 게시글 댓글 입력 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="댓글 삭제", notes = "댓글을 삭제하고 결과(success: 삭제 성공, fail: 삭제 실패)를 반환, comment식별자와 user식별자 사용", response = String.class)
    @DeleteMapping("/remove/{commentId}/{userId}")
    public ResponseEntity<String> remove(@PathVariable int commentId, @PathVariable int userId) {
        logger.debug("[ 피드백 게시판 댓글 삭제 호출 ]");
        System.out.println(">>> FeedbackcommentController remove : commentId = " + commentId + "   userId : " + userId);

        boolean result = feedbackcommentService.delete(commentId, userId);

        if (result) {
            System.out.println(">>> 피드백 게시글 댓글 삭제 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 피드백 게시글 댓글 삭제 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="해당 댓글 전부 가져오기", notes = "선택된 피드백 게시글의 댓글 내용을 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = FeedbackcommentRes.class)
    @GetMapping("/filterInfo/{feedbackId}")
    public ResponseEntity<List<FeedbackcommentRes>> filterInfo(@PathVariable int feedbackId) {
        logger.debug("[ 해당 피드백 게시글 댓글 모든 정보 호출 ]");
        System.out.println(">>> FeedbackcommentController filterInfo : feedbackId = " + feedbackId);

        List<FeedbackcommentRes> commentList = feedbackcommentService.getFilteringInfo(feedbackId);

        if (commentList == null) {
            System.out.println(">>> 해당 피드백 댓글 정보 얻기 실패");
            return (new ResponseEntity<List<FeedbackcommentRes>>(commentList, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 해당 피드백 댓글 정보 얻기 성공");
            commentList.forEach(System.out::println);
            return (new ResponseEntity<List<FeedbackcommentRes>>(commentList, HttpStatus.OK));
        }
    }

    @ApiOperation(value="피드백 댓글 좋아요", notes = "선택된 피드백 게시글의 댓글의 좋아요(pick = true)로 변경)", response = String.class)
    @GetMapping("/pick/{commentId}/{userId}")
    public ResponseEntity<String> pick(@PathVariable int commentId, @PathVariable int userId) {
        logger.debug("[ 피드백 게시판 댓글 좋아요 호출 ]");
        System.out.println(">>> FeedbackcommentController pick : commentId = " + commentId + "   userId : " + userId);

        boolean result = feedbackcommentService.pick(commentId, userId);

        if (result) {
            System.out.println(">>> 피드백 게시글 댓글 좋아요 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 피드백 게시글 댓글 좋아요 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="피드백 댓글 좋아요 취소", notes = "선택된 피드백 게시글의 댓글의 좋아요(pick = false)로 변경)", response = String.class)
    @GetMapping("/unpick/{commentId}/{userId}")
    public ResponseEntity<String> unpick(@PathVariable int commentId, @PathVariable int userId) {
        logger.debug("[ 피드백 게시판 댓글 좋아요 취소 호출 ]");
        System.out.println(">>> FeedbackcommentController unpick : commentId = " + commentId + "   userId : " + userId);

        boolean result = feedbackcommentService.unpick(commentId, userId);

        if (result) {
            System.out.println(">>> 피드백 게시글 댓글 좋아요 취소 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 피드백 게시글 댓글 좋아요 취소 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }
}
