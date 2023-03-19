package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.ReviewReq;
import com.ssafy.init.api.response.FeedbackRes;
import com.ssafy.init.api.response.ReviewRes;
import com.ssafy.init.api.service.ReviewService;

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

// [smj]
@Api("면접 후기 게시판 서비스 API")
@RestController
@RequestMapping("/review")
public class ReviewController {
    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private ReviewService boardService;

    @ApiOperation(value="게시글 작성", notes = "게시글을 작성(id 제외 4개 데이터 필수 입력)하고 결과(0포함 양의 정수: 작성 성공, -1: 작성 실패)를 반환", response = Integer.class)
    @PostMapping("/write")
    public ResponseEntity<Integer> write(@RequestBody ReviewReq boardReq) {
        logger.debug("[ 후기 게시판 글 작성 호출 ]");
//        System.out.println(">>> ReviewController write : " + boardReq);

        int result = boardService.insert(boardReq);

        if (result >= 0) {
//            System.out.println(">>> 글 입력 성공");
            return (new ResponseEntity<Integer>(result, HttpStatus.OK));
        }
        else {
//            System.out.println(">>> 글 입력 실패");
            return (new ResponseEntity<Integer>(result, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="게시글 수정", notes = "게시글을 수정하고 결과(success: 수정 성공, fail: 수정 실패)를 반환", response = String.class)
    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody ReviewReq boardReq) {
        logger.debug("[ 후기 게시판 글 수정 호출 ]");
//        System.out.println(">>> ReviewController modify : " + boardReq);

        int result = boardService.modify(boardReq);

        if (result == -1) {
//            System.out.println(">>> 글 수정 실패(권한이 없는 요청)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST));
        }
        else if (result == 0) {
//            System.out.println(">>> 글 수정 실패(서버 에러)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
        else {
//            System.out.println(">>> 글 수정 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 삭제", notes = "게시글을 삭제하고 결과(success: 삭제 성공, fail: 삭제 실패)를 반환, board식별자와 user식별자 사용", response = String.class)
    @DeleteMapping("/remove/{boardId}/{userId}")
    public ResponseEntity<String> remove(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 후기 게시판 글 삭제 호출 ]");
//        System.out.println(">>> ReviewController remove : reviewId = " + boardId + "   userId : " + userId);

        int result = boardService.delete(boardId, userId);

        if (result == -1) {
//            System.out.println(">>> 글 삭제 실패(권한이 없는 요청)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST));
        }
        else if (result == 0) {
//            System.out.println(">>> 글 삭제 실패(서버 에러)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
        else {
//            System.out.println(">>> 글 삭제 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 상세보기", notes = "한 개의 게시글을 택해 해당 게시글의 상세정보 확인(실패 시 null 반환), board식별자와 user식별자 사용", response = ReviewRes.class)
    @GetMapping("/find/{boardId}/{userId}")
    public ResponseEntity<ReviewRes> find(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 후기 게시판 글 단일 정보 호출 ]");
//        System.out.println(">>> ReviewController find : reviewId = " + boardId +  "   userId : " + userId);

        ReviewRes boardRes = boardService.getInfo(boardId, userId);

        if (boardRes == null) {
//            System.out.println(">>> 글 정보 얻기 실패");
            return (new ResponseEntity<ReviewRes>(boardRes, HttpStatus.NO_CONTENT));
        }
        else {
//            System.out.println(">>> 글 정보 얻기 성공 : " + boardRes);
            return (new ResponseEntity<ReviewRes>(boardRes, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 전부 가져오기", notes = "게시글 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = ReviewRes.class)
    @GetMapping("/findAll")
    public ResponseEntity<List<ReviewRes>> findAll() {
        logger.debug("[ 후기 게시판 글 모든 정보 호출 ]");
//        System.out.println(">>> ReviewController findAll");

        List<ReviewRes> boardList = boardService.getAllInfo();

        if (boardList == null) {
//            System.out.println(">>> 글 전체 정보 얻기 실패");
            return (new ResponseEntity<List<ReviewRes>>(boardList, HttpStatus.NO_CONTENT));
        }
        else {
//            System.out.println(">>> 글 전체 정보 얻기 성공");
//            boardList.forEach(System.out::println);
            return (new ResponseEntity<List<ReviewRes>>(boardList, HttpStatus.OK));
        }
    }

    //[psh]
    @ApiOperation(value="면접 후기 게시글 페이징", notes = "면접 후기 게시판 내용 페이지 별로 내용 반환. (page=0(원하는 페이지, 인덱스 0부터 시작, default), size=10(default) requestparam형식으로 보낼 것(ex: page=0&size=3)", response = Page.class)
    @GetMapping("/paging")
    public ResponseEntity<Page<ReviewRes>> processPaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(">>> processPaging(review) : " + pageable);
        Page<ReviewRes> result = boardService.pageList(pageable);

        System.out.println("<<<<< processPaging(review) >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of processPaging(review) >>>>>");

        if (result.hasContent()) return (new ResponseEntity<Page<ReviewRes>>(result, HttpStatus.OK));
        else return(new ResponseEntity<Page<ReviewRes>>(result, HttpStatus.NO_CONTENT));
    }
}
