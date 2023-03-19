package com.ssafy.init.api.service;

import java.util.List;

import com.ssafy.init.api.request.ChecklistReq;
import com.ssafy.init.api.request.RealReportReq;
import com.ssafy.init.api.request.ReportReq;
import com.ssafy.init.api.response.AnswerRes;
import com.ssafy.init.api.response.ChecklistRes;
import com.ssafy.init.api.response.RealReportRes;
import com.ssafy.init.db.entity.Checklist;
import com.ssafy.init.db.entity.RealQa;
import com.ssafy.init.db.entity.RealReport;
import com.ssafy.init.db.entity.Report;

public interface RealReportService {
	
	// Report에 RealReportReq 넣기
	Report fillReport(Report report, RealReportReq realReportReq);
	
	// RealReport에 RealReportReq 넣기
	RealReport fillRealReport(RealReport realReport, RealReportReq realReportReq);
	
	// RealQa에 RealReportReq 넣기
	RealQa fillRealQa(RealQa realQa, RealReportReq realReportReq, int idx);

	// RealReportRes에 Report, RealReport, RealQa 넣기
	RealReportRes fillRealReportRes(Report report, RealReport realReport, List<RealQa> realQaList);
	
	// RealReportRes에 ReportReq 넣기
	RealReportReq fillRealReportReq(RealReportRes realReportRes);

	
	// 실전 면접 일지 생성
	RealReportRes create(RealReportReq realReportReq);
	
	// 실전 면접 일지 상세 조회  (면접 일지 리스트는 reportService 에서 구현하자☆★☆)
	RealReportRes detail(int realReportId);
	
	// 실전 면접 일지 수정
	RealReportRes update(RealReportRes realReportRes);
	
	// 실전 면접 일지 삭제
	String delete(int realReportId, int userId);
	

}
