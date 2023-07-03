package com.goodday.proj.api.mypage.service;

import java.util.Map;

public interface MyPageService {
    Map<String, Object> myBoardListPaging(Long memberNo, Integer currentPage);

    Map<String, Object> myMeetingList(Integer currentPage, Long memberNo);

    Map<String, Object> myHelpListPaging(Integer currentPage, Long memberNo);

    Map<String, Object> myOrderListPaging(Integer currentPage, Long memberNo);

    Map<String, Object> myReplyListPaging(Long memberNo, Integer currentPage);
}
