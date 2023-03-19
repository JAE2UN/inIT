package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.ReviewcommentReq;
import com.ssafy.init.api.response.ReviewcommentRes;
import com.ssafy.init.api.service.ReviewcommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("후기 게시판 댓글 API")
@RestController
@RequestMapping("/reviewcomment")
public class ReviewcommentController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private ReviewcommentService boardcommentService;

    //CR(all)D
    @ApiOperation(value="댓글 작성", notes = "댓글을 작성하고 결과(success: 작성 성공, fail: 작성 실패)를 반환", response = String.class)
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody ReviewcommentReq boardcommentReq) {
        logger.debug("[ 후기 게시판 댓글 작성 호출 ]");
//        System.out.println(">>> ReviewcommentController write : " + boardcommentReq);

        boolean result = boardcommentService.insert(boardcommentReq);

        if (result) {
//            System.out.println(">>> 댓글 입력 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
//            System.out.println(">>> 댓글 입력 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="댓글 삭제", notes = "댓글을 삭제하고 결과(success: 삭제 성공, fail: 삭제 실패)를 반환, comment식별자와 user식별자 사용", response = String.class)
    @DeleteMapping("/remove/{commentId}/{userId}")
    public ResponseEntity<String> remove(@PathVariable int commentId, @PathVariable int userId) {
        logger.debug("[ 후기 게시판 글 삭제 호출 ]");
//        System.out.println(">>> ReviewcommentController remove : commentId = " + commentId + "   userId : " + userId);

        boolean result = boardcommentService.delete(commentId, userId);

        if (result) {
//            System.out.println(">>> 댓글 삭제 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
//            System.out.println(">>> 댓글 삭제 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="해당 댓글 전부 가져오기", notes = "선택된 자유 게시글의 댓글 내용을 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = ReviewcommentRes.class)
    @GetMapping("/filterInfo/{boardId}")
    public ResponseEntity<List<ReviewcommentRes>> filterInfo(@PathVariable int boardId) {
        logger.debug("[ 해당 게시글 댓글 모든 정보 호출 ]");
//        System.out.println(">>> ReviewcommentController filterInfo : boardId = " + boardId);

        List<ReviewcommentRes> commentList = boardcommentService.getFilteringInfo(boardId);

        if (commentList == null) {
//            System.out.println(">>> 해당 댓글 정보 얻기 실패");
            return (new ResponseEntity<List<ReviewcommentRes>>(commentList, HttpStatus.NO_CONTENT));
        }
        else {
//            System.out.println(">>> 해당 댓글 정보 얻기 성공");
//            commentList.forEach(System.out::println);
            return (new ResponseEntity<List<ReviewcommentRes>>(commentList, HttpStatus.OK));
        }
    }
}
