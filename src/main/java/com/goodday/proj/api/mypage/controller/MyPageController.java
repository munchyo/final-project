package com.goodday.proj.api.mypage.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.mypage.dto.TodoListDto;
import com.goodday.proj.api.mypage.model.TodoList;
import com.goodday.proj.api.mypage.repository.MyPageRepository;
import com.goodday.proj.api.mypage.service.MyPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
public class MyPageController {

    private final MyPageService myPageService;
    private final MyPageRepository myPageRepository;

    @GetMapping("/board/{memberNo}")
    public Map<String, Object> myBoardList(@PathVariable Long memberNo, @RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }

        return myPageService.myBoardListPaging(memberNo, currentPage);
    }

    @GetMapping("/todo/{memberNo}")
    public List<TodoList> myTodoList(@PathVariable Long memberNo) {
        return myPageRepository.findMyTodoListByMemberNo(memberNo);
    }

    @PostMapping("/todo/{memberNo}")
    public void addTodoList(@PathVariable Long memberNo, @Valid @RequestBody List<TodoListDto> todoListDtoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(ErrorConst.nullError);
        }

        for (TodoListDto todoListDto : todoListDtoList) {
            TodoList todoList =
                    new TodoList(todoListDto.getCalDate(), todoListDto.getPeriod(), todoListDto.getGoal(), memberNo);
            myPageRepository.addTodoList(todoList);
        }
    }

    @PatchMapping("/todo/{memberNo}")
    public void editTodoListStatus(@PathVariable Long memberNo, @RequestParam Long calNo, @RequestParam Integer calStatus) {
        if (calNo == null || calStatus == null) {
            throw new RuntimeException(ErrorConst.nullError);
        } else if (calStatus > 2 || calStatus < 1) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        Map<String, Object> editTodoList = new HashMap<>();
        editTodoList.put("memberNo", memberNo);
        editTodoList.put("calNo", calNo);
        editTodoList.put("calStatus", calStatus);

        myPageRepository.updateTodoListStatus(editTodoList);
    }

    @DeleteMapping("/todo/{memberNo}")
    public void deleteTodoList(@PathVariable Long memberNo, @RequestParam Long calNo) {
        if (calNo == null) {
            throw new RuntimeException(ErrorConst.nullError);
        }

        Map<String, Object> deleteTodoList = new HashMap<>();
        deleteTodoList.put("memberNo", memberNo);
        deleteTodoList.put("calNo", calNo);

        myPageRepository.deleteTodoList(deleteTodoList);
    }

    @GetMapping("/meet/{memberNo}")
    public Map<String, Object> myMeeting(@PathVariable Long memberNo, @RequestParam(required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }
        return myPageService.myMeetingList(currentPage, memberNo);
    }

    // TODO 주문내역, 내댓글
}
