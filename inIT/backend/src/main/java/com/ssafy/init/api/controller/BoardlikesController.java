package com.ssafy.init.api.controller;

import com.ssafy.init.api.service.BoardlikesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//[psh]
@Api("자유 & 정보 게시글 추천 API")
@RestController
@RequestMapping("/boardlikes")
public class BoardlikesController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BoardlikesService boardlikesService;

    @ApiOperation(value="게시글 추천 등록", notes = "게시글 추천 등록하고 결과(양의 정수: 등록 성공, -1: 등록 실패)를 반환 결과는 게시글의 전체 좋아요 개수, board식별자와 user식별자 사용, 정보 카테고리의 게시글이 일정 개수 이상 추천을 받으면 정보게시판에 게시글 등록", response = String.class)
    @GetMapping("/recommend/{boardId}/{userId}")
    public ResponseEntity<Integer> recommend(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 게시판 추천 등록 호출 ]");
        System.out.println(">>> BoardlikesController recommend : boardId = " + boardId + "   userId : " + userId);

        int result = boardlikesService.insert(boardId, userId);

        if (result > 0) {
            System.out.println(">>> 추천 등록 성공");
             return (new ResponseEntity<Integer>(result, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 추천 등록 실패");
            return (new ResponseEntity<Integer>(result, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="게시글 추천 삭제", notes = "게시글 추천 삭제하고 결과(0이상 양의 정수: 삭제 성공, -1: 삭제 실패)를 반환 결과는 게시글의 전체 좋아요 개수, board식별자와 user식별자 사용, 정보 카테고리의 게시글이 일정 개수 미만 추천수면 정보게시판에서 게시글 삭제", response = String.class)
    @DeleteMapping("/remove/{boardId}/{userId}")
    public ResponseEntity<Integer> remove(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 게시판 추천 삭제 호출 ]");
        System.out.println(">>> BoardlikesController remove : boardId = " + boardId + "   userId : " + userId);

        int result = boardlikesService.delete(boardId, userId);

        if (result >= 0) {
            System.out.println(">>> 추천 삭제 성공");
            return (new ResponseEntity<Integer>(result, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 추천 삭제 실패");
            return (new ResponseEntity<Integer>(result, HttpStatus.NO_CONTENT));
        }
    }
}