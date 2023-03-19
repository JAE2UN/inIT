package com.ssafy.init.api.controller;

import com.ssafy.init.api.service.UserService;
import com.ssafy.init.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("회원 서비스 API")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired private UserService userService;


    @ApiOperation(value="회원가입", notes = "email, nick, pw 필수 입력. 성공 시 success, 실패 시 fail 반환", response = String.class)
    //[smj] 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody User userInfo) {
        logger.debug("[ 회원가입 프로세스 호출 ]");

//        System.out.println(">>>> controller user : " + userInfo);

        String result = userService.registerUser(userInfo);
        
        if(result.equals(SUCCESS)) {
        	return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }

    //[smj] 로그인
    @ApiOperation(value = "로그인",
    		notes = "email, pw 필수 입력. 상태에 맞는 결과(0: 아이디  불일치, 1: 비밀번호 불일치, 2: 모두 일치)와 유저 정보(pw, created_at, updated_at 제외)를 반환",
    		response = Map.class)
    @PostMapping("/login")
    public ResponseEntity<Map<Integer, User>> login(@RequestBody User userInfo) {
        logger.debug("[ 로그인 프로세스 호출 ]");

        // status 0 = 아이디  불일치, status 1 = 비밀번호 불일치, status 2 = 모두 일치
        int status = userService.checkInfo(userInfo.getEmail(), userInfo.getPw());
        // 상태와 유저 정보 담을 map
        Map<Integer, User> result = new HashMap<Integer, User>();
        
        
        if(status < 2) { // 0: 아이디 불일치, 1: 비밀번호 불일치
        	result.put(status, null);
            return new ResponseEntity<Map<Integer, User>>(result, HttpStatus.ACCEPTED);
        } else if(status == 2){ // 모두 일치
        	User returnUserInfo = userService.getUser(userInfo.getEmail());
        	
        	result.put(status, userService.sendUser(returnUserInfo));
            return new ResponseEntity<Map<Integer, User>>(result, HttpStatus.OK);
        } else { // 그 외 (경우 다시 생각해서 수정하기 ☆★☆)
        	result.put(4, null);
            return new ResponseEntity<Map<Integer, User>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //[smj] email 중복 확인
    @ApiOperation(value="email 중복 확인", notes = "email 중복 확인을 진행하고 결과(success: 중복X, fail: 중복O)를 반환", response = String.class)
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<String> checkUserId(@PathVariable String email) {
        logger.debug("[ email 중복 확인 프로세스 호출 ]");
//        System.out.println(email);
        User user = userService.getUser(email);
        if(user == null) {
        	return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
        } else {
        	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }
    
	//[smj] nick 중복 확인
	@ApiOperation(value="nick 중복 확인", notes = "nick 중복 확인을 진행하고 결과(success: 중복X, fail: 중복O)를 반환", response = String.class)
	@GetMapping("/checkNick/{nick}")
	public ResponseEntity<String> checkNick(@PathVariable String nick) {
	    logger.debug("[ nick 중복 확인 프로세스 호출 ]");
	
	    // 입력된 nick을 갖는 데이터 개수
	    int dataCnt = userService.checkNick(nick);
	    if(dataCnt == 0) {
	    	return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
	    } else {
	    	return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	    }
	}
    
	// [smj] 사용자 정보 조회
	@ApiOperation(value="사용자 정보 조회", notes = "입력된 email에 맞는 사용자 정보(pw, created_at, updated_at 제외)를 반환(없는 user_id가 입력되면 400 코드만 반환)", response = String.class)
	@GetMapping("/detailUserInfo/{email}")
	public ResponseEntity<User> detail(@PathVariable String email) {
	    logger.debug("[ 사용자 정보 조회 프로세스 호출 ]");
	    
	    User returnUserInfo = userService.getUser(email);
	    
	    if(returnUserInfo != null) {	    	
	    	return new ResponseEntity<User>(userService.sendUser(returnUserInfo), HttpStatus.OK);
        } else {
        	return new ResponseEntity<User>(userService.sendUser(returnUserInfo), HttpStatus.BAD_REQUEST);
        }
	}
	
	// [smj] 사용자 정보 변경
	@ApiOperation(value="사용자 정보 변경", notes = "email, imgUrl, nick, statusMsg 필수 입력. 사용자 정보(pw, created_at, updated_at 제외)를 반환", response = User.class)
	@PostMapping("/updateUserInfo")
	public ResponseEntity<User> update(@RequestBody User userInfo) {
	    logger.debug("[ 사용자 정보 변경 프로세스 호출 ]");
	    
	    User returnUserInfo = userService.updateUser(userInfo);
	    
	    if(returnUserInfo != null) {
	    	return new ResponseEntity<User>(userService.sendUser(returnUserInfo), HttpStatus.OK);
        } else {
        	return new ResponseEntity<User>(userService.sendUser(returnUserInfo), HttpStatus.BAD_REQUEST);
        }
	}
	
	
}
