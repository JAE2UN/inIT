package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.BoardReq;
import com.ssafy.init.api.request.FeedbackReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.api.response.FeedbackRes;
import com.ssafy.init.api.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//[psh]
@Api("피드백 게시판 서비스 API")
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private FeedbackService feedbackService;

    //Create Read ReadAll Update Delete

    @ApiOperation(value="게시글 작성", notes = "게시글을 작성하고 결과(0포함 양의 정수: 작성 성공, -1: 작성 실패)를 반환", response = Integer.class)
    @PostMapping("/write")
    public ResponseEntity<Integer> write(@RequestBody FeedbackReq feedbackReq) {
        logger.debug("[ 피드백게시판 글 작성 호출 ]");
        System.out.println(">>> FeedbackController write : " + feedbackReq);

        int result = feedbackService.insert(feedbackReq);

        if (result >= 0) {
            System.out.println(">>> 글 입력 성공");
            return (new ResponseEntity<Integer>(result, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 글 입력 실패");
            return (new ResponseEntity<Integer>(result, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="게시글 수정", notes = "게시글을 수정하고 결과(success: 수정 성공, fail: 수정 실패)를 반환", response = String.class)
    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody FeedbackReq feedbackReq) {
        logger.debug("[ 피드백게시판 글 수정 호출 ]");
        System.out.println(">>> FeedbackController modify : " + feedbackReq);

        int result = feedbackService.modify(feedbackReq);

        if (result == -1) {
            System.out.println(">>> 글 수정 실패(권한이 없는 요청)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST));
        }
        else if (result == 0) {
            System.out.println(">>> 글 수정 실패(서버 에러)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 글 수정 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 삭제", notes = "게시글을 삭제하고 결과(success: 삭제 성공, fail: 삭제 실패)를 반환, feedback식별자와 user식별자 사용", response = String.class)
    @DeleteMapping("/remove/{feedbackId}/{userId}")
    public ResponseEntity<String> remove(@PathVariable int feedbackId, @PathVariable int userId) {
        logger.debug("[ 피드백게시판 글 삭제 호출 ]");
        System.out.println(">>> FeedbackController remove : feedbackId = " + feedbackId + "   userId : " + userId);

        int result = feedbackService.delete(feedbackId, userId);

        if (result == -1) {
            System.out.println(">>> 피드백 글 삭제 실패(권한이 없는 요청)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST));
        }
        else if (result == 0) {
            System.out.println(">>> 피드백 글 삭제 실패(서버 에러)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 피드백 글 삭제 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 상세보기", notes = "한 개의 게시글을 택해 해당 게시글의 상세정보 확인(실패 시 null 반환), feedback식별자 사용", response = FeedbackRes.class)
    @GetMapping("/find/{feedbackId}")
    public ResponseEntity<FeedbackRes> find(@PathVariable int feedbackId) {
        logger.debug("[ 피드백게시판 글 단일 정보 호출 ]");
        System.out.println(">>> FeedbackController find : feedbackId = " + feedbackId);

        FeedbackRes feedbackRes = feedbackService.getInfo(feedbackId);

        if (feedbackRes == null) {
            System.out.println(">>> 피드백 글 정보 얻기 실패");
            return (new ResponseEntity<FeedbackRes>(feedbackRes, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 피드백 글 정보 얻기 성공 : " + feedbackRes);
            return (new ResponseEntity<FeedbackRes>(feedbackRes, HttpStatus.OK));
        }
    }

    @ApiOperation(value="피드백 게시글 전부 가져오기", notes = "피드백 게시글에 대한 내용을 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = FeedbackRes.class)
    @GetMapping("/findAll")
    public ResponseEntity<List<FeedbackRes>> findAll() {
        logger.debug("[ 피드백게시판 글 모든 정보 호출 ]");
        System.out.println(">>> FeedbackController findAll");

        List<FeedbackRes> feedbackList = feedbackService.getAllInfo();

        if (feedbackList == null) {
            System.out.println(">>> 피드백 글 전체 정보 얻기 실패");
            return (new ResponseEntity<List<FeedbackRes>>(feedbackList, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 피드백 글 전체 정보 얻기 성공");
            feedbackList.forEach(System.out::println);
            return (new ResponseEntity<List<FeedbackRes>>(feedbackList, HttpStatus.OK));
        }
    }

    @ApiOperation(value="피드백 게시글 페이징", notes = "피드백 게시판 내용 페이지 별로 내용 반환. (page=0(원하는 페이지, 인덱스 0부터 시작, default), size=10(default) requestparam형식으로 보낼 것(ex: page=0&size=3)", response = Page.class)
    @GetMapping("/paging")
    public ResponseEntity<Page<FeedbackRes>> processPaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(">>> processPaging(feedback) : " + pageable);
        Page<FeedbackRes> result = feedbackService.pageList(pageable);

        System.out.println("<<<<< processPaging(feedback) >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of processPaging(feedback) >>>>>");

        if (result.hasContent()) return (new ResponseEntity<Page<FeedbackRes>>(result, HttpStatus.OK));
        else return(new ResponseEntity<Page<FeedbackRes>>(result, HttpStatus.NO_CONTENT));
    }
}
