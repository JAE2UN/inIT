package com.ssafy.init.api.service;

public interface BoardlikesService {
    static final int STD_LIKES = 5;

    int insert(int boardId, int userId);

    int delete(int boardId, int userId);
}
