package com.ssafy.init.api.service;

import com.ssafy.init.api.request.BoardReq;
import com.ssafy.init.api.response.BoardRes;
import com.ssafy.init.db.entity.Board;
import com.ssafy.init.db.entity.Boardlikes;
import com.ssafy.init.db.entity.User;
import com.ssafy.init.db.repository.BoardRepository;
import com.ssafy.init.db.repository.BoardlikesRepository;
import com.ssafy.init.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardlikesRepository boardlikesRepository;

    @Override
    @Transactional
    public int insert(BoardReq boardReq) {
        int userId = boardReq.getUserId();

        if (userId < 0 || userId > Integer.MAX_VALUE) return (-1);
        try {
            Board board = new Board();
            User user = new User();
            int resultId = - 1;

            board.setType(boardReq.getType());
            board.setTitle(boardReq.getTitle());
            board.setContent(boardReq.getContent());

            user.setId(boardReq.getUserId());
            board.setUser(user);

            resultId = boardRepository.save(board).getId();

            return (resultId);
        } catch (IllegalArgumentException iae) {
            System.out.println(">>> 글 작성 중 저장 에러");
            return (-1);
        } catch (Exception e) {
            System.out.println(">>> 글 작성 중 전역 에러");
            return (-1);
        }
    }

    //수정 - Update : 글 수정 시 권한 없는 요청(-1), 수정 실패(0) / 성공(1) 여부를 반환
    @Override
    public int modify(BoardReq newBoard) {
        int boardId = newBoard.getId();

        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (-1);

        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            if (newBoard.getUserId() != board.get().getUser().getId()) return (-1);

            if (newBoard.getType() != 0)    board.get().setType(newBoard.getType());
            if (newBoard.getTitle() != null)   board.get().setTitle(newBoard.getTitle());
            if (newBoard.getContent() != null)  board.get().setContent(newBoard.getContent());
            try {
                boardRepository.save(board.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 글 수정 중 저장 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 글 수정 중 전역 에러");
                return (0);
            }
        }
        return (-1);
    }

    //삭제 - Delete : 글 삭제 시 권한 없는 요청(-1), 삭제 실패(0) / 성공(1) 여부를 반환
    @Override
    public int delete(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (-1);

        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()) {
            if (board.get().getUser().getId() != userId) return (-1);

            try {
                boardRepository.delete(board.get());
                return (1);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 글 삭제 중 처리 에러");
                return (0);
            } catch (Exception e) {
                System.out.println(">>> 글 수정 중 전역 에러");
                return (0);
            }
        }
        return (-1);
    }

    private BoardRes convertBoardRes(Board board) {
        Optional<User> user = userRepository.findById(board.getUser().getId());
        BoardRes boardRes = new BoardRes();

        if (user.isPresent()) {
            boardRes.setUserNick(user.get().getNick());
            boardRes.setUserGrade(user.get().getGrade());
            boardRes.setUserTemperature(user.get().getTemperature());

            boardRes.setId(board.getId());
            
            // [smj] dateTime -> date (String)
            String onlyDate = board.getCreatedAt().toString().substring(2, 10);
            boardRes.setCreateTime(onlyDate);
//            boardRes.setCreateTime(board.getCreatedAt());
            
            boardRes.setUpdateTime(board.getUpdatedAt());
            boardRes.setType(board.getType());
            boardRes.setTitle(board.getTitle());
            boardRes.setContent(board.getContent());
            boardRes.setInfo(board.isInfo());
            boardRes.setLikesCnt(board.getLikes_cnt());
            boardRes.setCommentCnt(board.getComment_cnt());

            return (boardRes);
        }
        return (null);
    }

    @Override
    public BoardRes getInfo(int boardId, int userId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE || userId < 0 || userId > Integer.MAX_VALUE) return (null);

        Optional<Board> board = boardRepository.findById(boardId);
        if(board.isPresent()) {
            BoardRes boardRes = convertBoardRes(board.get());
            Boardlikes boardlikes = boardlikesRepository.findByBoardIdAndUserId(boardId, userId);

            if (boardlikes == null) boardRes.setUserAlreadyPush(false);
            else boardRes.setUserAlreadyPush(true);

            return (boardRes);
        }
        return (null);
    }

    @Override
    public List<BoardRes> getAllInfo() {
        List<Board> boardList = boardRepository.findAllByOrderByIdDesc();
        List<BoardRes> boardResList = new ArrayList<>();

        for (int i = 0; i < boardList.size(); i++) {
            BoardRes boardRes = convertBoardRes(boardList.get(i));
            if (boardRes != null) boardResList.add(boardRes);
//            else return (null);
        }
        return (boardResList);
    }

    @Override
    public List<BoardRes> getAllFilterInfo() {
        List<Board> boardList = boardRepository.findAllByIsInfoOrderByIdDesc(true);
        List<BoardRes> boardResList = new ArrayList<>();

        for (int i = 0; i < boardList.size(); i++) {
            BoardRes boardRes = convertBoardRes(boardList.get(i));
            if (boardRes != null) boardResList.add(boardRes);
            //            else return (null);
        }
        return (boardResList);
    }

    @Override
    public List<BoardRes> getAllFilterType(int type) {
        if (type < 1 || type > 3)   return (null);

        List<Board> boardList = boardRepository.findAllByTypeOrderByIdDesc(type);
        List<BoardRes> boardResList = new ArrayList<>();

        for (int i = 0; i < boardList.size(); i++) {
            BoardRes boardRes = convertBoardRes(boardList.get(i));
            if (boardRes != null) boardResList.add(boardRes);
            //            else return (null);
        }
        return (boardResList);
    }

    @Override
    public boolean insertInfo(BoardReq boardReq) {
        int userId = boardReq.getUserId();

        if (userId < 0 || userId > Integer.MAX_VALUE) return (false);

        Optional<User> userInfo = userRepository.findById(userId);
        System.out.println(">>> 정보게시판에 글 입력 시도 : " + userInfo.get());
        if (userInfo.isPresent()) {
            String userNick = userInfo.get().getNick();

            if (!ADMIN.equals(userNick))  return (false);

            try {
                Board board = new Board();
                User user = new User();

                board.setType(-1);
                board.setTitle(boardReq.getTitle());
                board.setContent(boardReq.getContent());

                user.setId(boardReq.getUserId());
                board.setUser(user);

                boardRepository.save(board);
                return (true);
            } catch (IllegalArgumentException iae) {
                System.out.println(">>> 글 작성 중 저장 에러");
                return (false);
            } catch (Exception e) {
                System.out.println(">>> 글 작성 중 전역 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean turnOnInfo(int boardId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setInfo(true);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 정보 게시판 정보 변경(On) 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean turnOffInfo(int boardId) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setInfo(false);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 정보 게시판 정보 변경(Off) 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean changeLikesCnt(int boardId, int likesCnt) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setLikes_cnt(likesCnt);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 게시판 추천 개수 수정 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    public boolean changeCommentCnt(int boardId, int commentCnt) {
        if (boardId < 0 || boardId > Integer.MAX_VALUE) return (false);

        Optional<Board> board = boardRepository.findById(boardId);
        if (board.isPresent()) {
            board.get().setComment_cnt(commentCnt);

            try {
                boardRepository.save(board.get());
            } catch (Exception e) {
                System.out.println(">>> 게시판 댓글 개수 수정 중 에러");
                return (false);
            }
        }
        return (false);
    }

    @Override
    @Transactional
    public Page<BoardRes> pageList(Pageable pageable) {
        return (boardRepository.findAll(pageable).map(board -> { return (convertBoardRes(board)); }));
    }

    @Override
    @Transactional
    public Page<Board> cmppageList(Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(1, 3);
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        System.out.println(pageRequest);
        return boardRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public Page<BoardRes> pageInfoList(Pageable pageable) {
        return (boardRepository.findAllByIsInfo(true, pageable).map(infoBoard -> { return (convertBoardRes(infoBoard)); }));
    }

    @Override
    public Page<BoardRes> pageTypeList(Pageable pageable, int type) {
        return (boardRepository.findAllByType(type, pageable).map(typeBoard -> { return (convertBoardRes(typeBoard)); }));
    }
}
