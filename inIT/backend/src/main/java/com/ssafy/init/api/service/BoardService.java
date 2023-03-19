package com.ssafy.init.api.service;

import com.ssafy.init.api.request.BoardReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.db.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface BoardService {
    static final String ADMIN = "admin";

    //작성 - Create : 글 작성 성공 시 글 번호, 실패 시 -1 반환
    int insert(BoardReq boardreq);

    //수정 - Update : 글 수정 시 권한 없는 요청(-1), 수정 실패(0) / 성공(1) 여부를 반환
    int modify(BoardReq boardReq);

    //삭제 - Delete : 글 삭제 시 권한 없는 요청, 삭제 실패 / 성공 여부를 반환
    int delete(int boardId, int userId);

    //읽기 - Read : 실패 시 null 반환
    BoardRes getInfo(int boardId, int userId);

    //읽기 - Read : 글 정보 전체 반환 실패 시 null 반환
    List<BoardRes> getAllInfo();

    List<BoardRes> getAllFilterInfo();

    List<BoardRes> getAllFilterType(int type);

    boolean insertInfo(BoardReq boardReq);

    boolean turnOnInfo(int boardId);

    boolean turnOffInfo(int boardId);

    boolean changeLikesCnt(int boardId, int likesCnt);

    boolean changeCommentCnt(int boardId, int commentCnt);

    Page<BoardRes> pageList(Pageable pageable);

    Page<Board> cmppageList(Pageable pageable);

    Page<BoardRes> pageInfoList(Pageable pageable);

    Page<BoardRes> pageTypeList(Pageable pageable, int type);
}
