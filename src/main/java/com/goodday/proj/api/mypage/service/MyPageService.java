package com.goodday.proj.api.mypage.service;

import java.util.Map;

public interface MyPageService {
    Map<String, Object> myBoardListPaging(Long memberNo, Integer currentPage);
}
