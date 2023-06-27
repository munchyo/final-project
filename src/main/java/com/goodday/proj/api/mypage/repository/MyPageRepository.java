package com.goodday.proj.api.mypage.repository;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.mypage.model.TodoList;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MyPageRepository {
    int countMyBoardListByMemberNo(Long memberNo);

    ArrayList<FreeBoard> findMyBoardListByMemberNo(Long memberNo, RowBounds rowBounds);

    List<TodoList> findMyTodoListByMemberNo(Long memberNo);

    int addTodoList(TodoList todoList);

    int updateTodoListStatus(Map<String, Object> editTodoList);

    int deleteTodoList(Map<String, Object> deleteTodoList);
}
