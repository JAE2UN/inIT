package com.ssafy.init.api.controller;

import com.ssafy.init.api.request.BoardReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.db.entity.Board;
import com.ssafy.init.api.service.BoardService;
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

import javax.xml.ws.Response;
import java.util.List;

//[psh]
@Api("자유 게시판 & 정보 게시판 서비스 API")
@RestController
@RequestMapping("/board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private BoardService boardService;

    @ApiOperation(value="게시글 작성", notes = "게시글을 작성하고 결과(0포함 양의 정수: 작성 성공, -1: 작성 실패)를 반환", response = Integer.class)
    @PostMapping("/write")
    public ResponseEntity<Integer> write(@RequestBody BoardReq boardReq) {
        logger.debug("[ 자유게시판 글 작성 호출 ]");
        System.out.println(">>> BoardController write : " + boardReq);

        int result = boardService.insert(boardReq);

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
    public ResponseEntity<String> modify(@RequestBody BoardReq boardReq) {
        logger.debug("[ 자유게시판 글 수정 호출 ]");
        System.out.println(">>> BoardController modify : " + boardReq);

        int result = boardService.modify(boardReq);

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

    @ApiOperation(value="게시글 삭제", notes = "게시글을 삭제하고 결과(success: 삭제 성공, fail: 삭제 실패)를 반환, board식별자와 user식별자 사용", response = String.class)
    @DeleteMapping("/remove/{boardId}/{userId}")
    public ResponseEntity<String> remove(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 자유게시판 글 삭제 호출 ]");
        System.out.println(">>> BoardController remove : boardId = " + boardId + "   userId : " + userId);

        int result = boardService.delete(boardId, userId);

        if (result == -1) {
            System.out.println(">>> 글 삭제 실패(권한이 없는 요청)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST));
        }
        else if (result == 0) {
            System.out.println(">>> 글 삭제 실패(서버 에러)");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 글 삭제 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
    }

    @ApiOperation(value="게시글 상세보기", notes = "한 개의 게시글을 택해 해당 게시글의 상세정보 확인(실패 시 null 반환), board식별자와 user식별자 사용", response = BoardRes.class)
    @GetMapping("/find/{boardId}/{userId}")
    public ResponseEntity<BoardRes> find(@PathVariable int boardId, @PathVariable int userId) {
        logger.debug("[ 자유게시판 글 단일 정보 호출 ]");
        System.out.println(">>> BoardController find : boardId = " + boardId +  "   userId : " + userId);

        BoardRes boardRes = boardService.getInfo(boardId, userId);

        if (boardRes == null) {
            System.out.println(">>> 글 정보 얻기 실패");
            return (new ResponseEntity<BoardRes>(boardRes, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 글 정보 얻기 성공 : " + boardRes);
            return (new ResponseEntity<BoardRes>(boardRes, HttpStatus.OK));
        }
    }

    @ApiOperation(value="자유 게시글 전부 가져오기", notes = "자유 게시글에 대한 내용을 타입 상관 없이 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = BoardRes.class)
    @GetMapping("/findAll")
    public ResponseEntity<List<BoardRes>> findAll() {
        logger.debug("[ 자유게시판 글 모든 정보 호출 ]");
        System.out.println(">>> BoardController findAll");

        List<BoardRes> boardList = boardService.getAllInfo();

        if (boardList == null) {
            System.out.println(">>> 글 전체 정보 얻기 실패");
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 글 전체 정보 얻기 성공");
            boardList.forEach(System.out::println);
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.OK));
        }
    }

    @ApiOperation(value="정보 게시글 전부 가져오기", notes = "정보 게시글에 업로드 된 글들을 리스트 형태로 가져옴(실패 시 null 반환)", response = BoardRes.class)
    @GetMapping("/findAll/filterInfo")
    public ResponseEntity<List<BoardRes>> findAllFilterInfo() {
        logger.debug("[ 정보게시판 글 모든 정보 호출 ]");
        System.out.println(">>> BoardController filterInfo");

        List<BoardRes> boardList = boardService.getAllFilterInfo();

        if (boardList == null) {
            System.out.println(">>> 정보 글 전체 정보 얻기 실패");
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 정보 글 전체 정보 얻기 성공");
            boardList.forEach(System.out::println);
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.OK));
        }
    }

    @ApiOperation(value="자유 게시글 내 선택 된 카테고리 게시글 전부 가져오기", notes = "선택 된 카테고리 게시글에 대한 내용을 전부 리스트 형태로 가져옴(실패 시 null 반환)", response = BoardRes.class)
    @GetMapping("/findAll/filterType/{type}")
    public ResponseEntity<List<BoardRes>> findAllFilterType(@PathVariable int type) {
        logger.debug("[ 카테고리 글 모든 정보 호출 ]");
        System.out.println(">>> BoardController filterTarget : type = " + type);

        List<BoardRes> boardList = boardService.getAllFilterType(type);

        if (boardList == null) {
            System.out.println(">>> 카테고리 글 정보 얻기 실패");
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.NO_CONTENT));
        }
        else {
            System.out.println(">>> 카테고리 글 정보 얻기 성공");
            boardList.forEach(System.out::println);
            return (new ResponseEntity<List<BoardRes>>(boardList, HttpStatus.OK));
        }
    }

    @ApiOperation(value="정보 게시글 작성", notes = "관리자가 정보 게시판에 게시글을 작성하고 결과(success: 작성 성공, fail: 작성 실패)를 반환, 관리자가 아니면 무조건 반환(관리자는 닉네임이 admin)", response = String.class)
    @PostMapping("/write/Info")
    public ResponseEntity<String> writeInfo(@RequestBody BoardReq boardReq) {
        logger.debug("[ 정보게시판 글 작성 호출(관리자) ]");
        System.out.println(">>> BoardController writeInfo : " + boardReq);

        boolean result = boardService.insertInfo(boardReq);

        if (result) {
            System.out.println(">>> 관리자 글(정보게시판) 입력 성공");
            return (new ResponseEntity<String>(SUCCESS, HttpStatus.OK));
        }
        else {
            System.out.println(">>> 관리자 글(정보게시판) 입력 실패");
            return (new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT));
        }
    }

    @ApiOperation(value="자유 게시글 페이징", notes = "자유 게시판 내용 페이지 별로 내용 반환. (page=0(원하는 페이지, 인덱스 0부터 시작, default), size=10(default) requestparam형식으로 보낼 것(ex: page=0&size=3)", response = Page.class)
    @GetMapping("/paging/free")
    public ResponseEntity<Page<BoardRes>> processPaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(">>> processPaging : " + pageable);
        Page<BoardRes> result = boardService.pageList(pageable);

        System.out.println("<<<<< processPaging >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of processPaging >>>>>");

        if (result.hasContent()) return (new ResponseEntity<Page<BoardRes>>(result, HttpStatus.OK));
        else return(new ResponseEntity<Page<BoardRes>>(result, HttpStatus.NO_CONTENT));
        //return (boardService.pageList(pageable));
    }

    @ApiOperation(value="정보 게시글 페이징", notes = "정보 게시판 내용 페이지 별로 내용 반환. (page=0(원하는 페이지, 인덱스 0부터 시작, default), size=10(default) requestparam형식으로 보낼 것(ex: page=0&size=3)", response = Page.class)
    @GetMapping("/paging/info")
    public ResponseEntity<Page<BoardRes>> processInfoPaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(">>> processInfoPaging : " + pageable);
        Page<BoardRes> result = boardService.pageInfoList(pageable);

        System.out.println("<<<<< processInfoPaging >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of processInfoPaging >>>>>");

        if (result.hasContent()) return (new ResponseEntity<Page<BoardRes>>(result, HttpStatus.OK));
        else return(new ResponseEntity<Page<BoardRes>>(result, HttpStatus.NO_CONTENT));
        //return (boardService.pageList(pageable));
    }

    @ApiOperation(value="카테고리 게시글 페이징", notes = "카테고리 별 게시판 내용 페이지 별로 내용 반환. (page=0(원하는 페이지, 인덱스 0부터 시작, default), size=10(default) requestparam형식으로 보낼 것(ex: page=0&size=3)", response = Page.class)
    @GetMapping("/paging/type/{type}")
    public ResponseEntity<Page<BoardRes>> processTypePaging(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable int type) {
        System.out.println(">>> processTypePaging : " + pageable + "   type : " + type);
        Page<BoardRes> result = boardService.pageTypeList(pageable, type);

        System.out.println("<<<<< processTypePaging >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of processTypePaging >>>>>");

        if (result.hasContent()) return (new ResponseEntity<Page<BoardRes>>(result, HttpStatus.OK));
        else return(new ResponseEntity<Page<BoardRes>>(result, HttpStatus.NO_CONTENT));
    }

    @GetMapping("/paging/test")
    public Page<Board> cmpPaging(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        System.out.println(">>> cmpPaging : " + pageable);
        Page<Board> result = boardService.cmppageList(pageable);

        System.out.println();
        System.out.println();

        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
        System.out.println(result.getSize());
        System.out.println(result.getContent());
        System.out.println(result.hasContent());
        System.out.println(result.isEmpty());
        if (result == null) System.out.println("result는 null입니다.");

        System.out.println();
        System.out.println();

        System.out.println("<<<<< cmp processPaging >>>>>>");
        result.forEach(System.out::println);
        System.out.println("<<<<< End of cmp processPaging >>>>>");

        return (result);

        //return boardService.cmppageList(pageable);
    }
}
