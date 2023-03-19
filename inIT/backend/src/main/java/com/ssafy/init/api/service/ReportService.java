package com.ssafy.init.api.service;

import java.util.List;

import com.ssafy.init.api.request.ReadyReq;
import com.ssafy.init.api.request.ReportReq;
import com.ssafy.init.api.response.ReportRes;

import com.ssafy.init.db.entity.Report;

public interface ReportService {


	// Report에 ReportReq 넣기
	Report fillReport(Report report, ReportReq reportReq);
	
	// ReportRes에 Report 넣기
	ReportRes fillReportRes(Report report);
	
	
	// 면접일지 생성 (면접일지 id 반환) - interviewController에서 사용
	int createReport(ReadyReq readyReq);
	
	// 면접일지 수정
	ReportRes updateReport(ReportReq reportReq);
	
	// 면접일지 조회 (상세보기)
	ReportRes getReport(int id);
	
	// 면접일지 리스트 조회
	List<ReportRes> getAllReport(int userId);
	
	// 면접일지 구분별 리스트 조회
	List<ReportRes> getAllReportByType(int userId, int type);
	
	// 면접일지 태그별 리스트 조회
	List<ReportRes> getAllReportByTag(int userId, String tag);
	
	// 면접일지 삭제
	String deleteReport(int id, int userId);

}
