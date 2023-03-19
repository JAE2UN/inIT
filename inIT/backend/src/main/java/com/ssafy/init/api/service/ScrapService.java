package com.ssafy.init.api.service;

import java.util.List;

import com.ssafy.init.api.request.ScrapReq;
import com.ssafy.init.api.response.ScrapRes;
import com.ssafy.init.db.entity.Scrap;

public interface ScrapService {
	
	
	// ScrapRes에 Scrap 넣기
	ScrapRes fillScrapRes(Scrap scrap);

	// 오답 노트 항목 추가 
    int insert(int questionId, int userId);

    // 오답 노트 조회 (리스트)
    List<ScrapRes> selectAll(int userId);
    
    // 오답 노트 항목 수정 (답변만 수정 가능한 듯)
    ScrapRes update(ScrapReq scrapReq);
    
    // 오답 노트 항목 삭제
    int delete(int userId, int scrapId);
}
