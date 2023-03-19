package com.ssafy.init.api.service;

import com.ssafy.init.api.request.WebrtcReq;
import com.ssafy.init.api.response.WebrtcRes;

import java.util.List;

public interface WebrtcService {
    boolean upload(WebrtcReq webrtcReq);

    List<WebrtcRes> getUrlList(int reportId, int userId);

    String getUrl(int reportId, int sequence);
}
