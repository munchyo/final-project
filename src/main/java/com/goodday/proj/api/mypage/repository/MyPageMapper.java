package com.goodday.proj.api.mypage.repository;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.mypage.model.TodoList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageMapper extends MyPageRepository {
    @Override
    int countMyBoardListByMemberNo(Long memberNo);

    @Override
    ArrayList<FreeBoard> findMyBoardListByMemberNo(Long memberNo, RowBounds rowBounds);

    @Override
    List<TodoList> findMyTodoListByMemberNo(Long memberNo);

    @Override
    int addTodoList(TodoList todoList);

    @Override
    int updateTodoListStatus(Map<String, Object> editTodoList);

    @Override
    int deleteTodoList(Map<String, Object> deleteTodoList);
}
