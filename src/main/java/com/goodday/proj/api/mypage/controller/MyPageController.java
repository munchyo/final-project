package com.goodday.proj.api.mypage.controller;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.mypage.dto.TodoListDto;
import com.goodday.proj.api.mypage.model.TodoList;
import com.goodday.proj.api.mypage.repository.MyPageRepository;
import com.goodday.proj.api.mypage.service.MyPageService;
import com.goodday.proj.api.order.model.Order;
import com.goodday.proj.api.order.repository.OrderRepository;
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
    private final OrderRepository orderRepository;

    /**
     * 내 게시글 보기
     *
     * @param memberNo
     * @param currentPage
     * @return
     */
    @GetMapping("/free/{memberNo}")
    public Map<String, Object> myFreeBoardList(@PathVariable Long memberNo, @RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return myPageService.myBoardListPaging(memberNo, currentPage);
    }

    /**
     * 내 댓글 보기
     * @param memberNo
     * @param currentPage
     * @return
     */
    @GetMapping("/free/reply/{memberNo}")
    public Map<String, Object> myReplyList(@PathVariable Long memberNo, @RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return myPageService.myReplyListPaging(memberNo, currentPage);
    }

    /**
     * 내 투두보기
     *
     * @param memberNo
     * @return
     */
    @GetMapping("/todo/{memberNo}")
    public List<TodoList> myTodoList(@PathVariable Long memberNo) {
        return myPageService.myTodoList(memberNo);
    }

    /**
     * 투두 추가
     *
     * @param memberNo
     * @param todoListDtoList
     * @param bindingResult
     */
    @PostMapping("/todo/{memberNo}")
    public void addTodoList(@PathVariable Long memberNo, @Valid @RequestBody List<TodoListDto> todoListDtoList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(ErrorConst.nullError);
        }

        int result = myPageService.addTodoList(memberNo, todoListDtoList);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 투두상태수정
     *
     * @param memberNo
     * @param calNo
     * @param calStatus
     */
    @PatchMapping("/todo/{memberNo}")
    public void editTodoListStatus(@PathVariable Long memberNo, @RequestParam Long calNo, @RequestParam Integer calStatus) {
        if (calNo == null || calStatus == null) {
            throw new RuntimeException(ErrorConst.nullError);
        } else if (calStatus > 2 || calStatus < 1) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        int result = myPageService.editTodoListStatus(memberNo, calNo, calStatus);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 투두삭제
     *
     * @param memberNo
     * @param calNo
     */
    @DeleteMapping("/todo/{memberNo}")
    public void deleteTodoList(@PathVariable Long memberNo, @RequestParam Long calNo) {
        if (calNo == null) {
            throw new RuntimeException(ErrorConst.nullError);
        }

        int result = myPageService.deleteTodoList(memberNo, calNo);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 내 모임목록보기
     *
     * @param memberNo
     * @param currentPage
     * @return
     */
    @GetMapping("/meet/{memberNo}")
    public Map<String, Object> myMeeting(@PathVariable Long memberNo, @RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return myPageService.myMeetingList(currentPage, memberNo);
    }

    /**
     * 내 1:1문의 목록
     *
     * @param memberNo
     * @param currentPage
     * @return Map
     */
    @GetMapping("/help/{memberNo}")
    public Map<String, Object> myHelp(@PathVariable Long memberNo, @RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return myPageService.myHelpListPaging(currentPage, memberNo);
    }

    /**
     * 내 주문 목록
     * @param currentPage
     * @param memberNo
     * @return
     */
    @PostMapping("/order-list")
    public Map<String, Object> myOrderList(@RequestParam(value = "page", required = false) Integer currentPage, @RequestParam Long memberNo) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return myPageService.myOrderListPaging(currentPage, memberNo);
    }

    /**
     * 주문 상세보기
     * @param orderNo
     * @return
     */
    @GetMapping("/order/{orderNo}")
    public Order orderView(@PathVariable Long orderNo) {
        return orderRepository.findOrderByOrderNo(orderNo);
    }

}
