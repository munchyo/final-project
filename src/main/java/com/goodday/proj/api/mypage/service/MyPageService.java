package com.goodday.proj.api.mypage.service;

import com.goodday.proj.api.mypage.dto.TodoListDto;
import com.goodday.proj.api.mypage.model.TodoList;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    Map<String, Object> myBoardListPaging(Long memberNo, Integer currentPage);

    Map<String, Object> myMeetingList(Integer currentPage, Long memberNo);

    Map<String, Object> myHelpListPaging(Integer currentPage, Long memberNo);

    Map<String, Object> myOrderListPaging(Integer currentPage, Long memberNo);

    Map<String, Object> myReplyListPaging(Long memberNo, Integer currentPage);

    List<TodoList> myTodoList(Long memberNo);

    int addTodoList(Long memberNo, List<TodoListDto> todoListDtoList);

    int editTodoListStatus(Long memberNo, Long calNo, Integer calStatus);

    int deleteTodoList(Long memberNo, Long calNo);
}
