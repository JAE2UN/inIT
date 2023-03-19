package com.ssafy.init.api.service;

public interface ReviewlikesService {
    static final int STD_LIKES = 1;

    int insert(int boardId, int userId);

    int delete(int boardId, int userId);
}
