package com.goodday.proj.api.mypage.service;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.free.model.FreeBoardReply;
import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.help.repository.HelpRepository;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.mypage.repository.MyPageRepository;
import com.goodday.proj.api.order.model.Order;
import com.goodday.proj.api.order.repository.OrderRepository;
import com.goodday.proj.pagination.Pagination;
import com.goodday.proj.pagination.model.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;
    private final MeetingRepository meetingRepository;
    private final HelpRepository helpRepository;
    private final OrderRepository orderRepository;

    @Override
    public Map<String, Object> myBoardListPaging(Long memberNo, Integer currentPage) {
        int listCount = myPageRepository.countMyBoardListByMemberNo(memberNo);
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 20);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<FreeBoard> freeBoards = myPageRepository.findMyBoardListByMemberNo(memberNo, rowBounds);

        Map<String, Object> pagingAndFreeBoards = new HashMap<>();
        pagingAndFreeBoards.put("pageInfo", pageInfo);
        pagingAndFreeBoards.put("freeBoards", freeBoards);
        return pagingAndFreeBoards;
    }

    @Override
    public Map<String, Object> myReplyListPaging(Long memberNo, Integer currentPage) {
        int listCount = myPageRepository.countMyReplyListByMemberNo(memberNo);
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 30);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<FreeBoardReply> replies = myPageRepository.findMyReplyListByMemberNo(memberNo, rowBounds);

        Map<String, Object> pagingAndReplies = new HashMap<>();
        pagingAndReplies.put("pageInfo", pageInfo);
        pagingAndReplies.put("replies", replies);
        return pagingAndReplies;
    }

    @Override
    public Map<String, Object> myMeetingList(Integer currentPage, Long memberNo) {
        int listCount = meetingRepository.countMyMeetingListByMemberNo(memberNo);
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<Meeting> meetings = meetingRepository.findMeetingListByMemberNo(memberNo, rowBounds);

        Map<String, Object> pageAndMeetingList = new HashMap<>();
        pageAndMeetingList.put("pageInfo", pageInfo);
        pageAndMeetingList.put("meetings", meetings);

        return pageAndMeetingList;
    }

    @Override
    public Map<String, Object> myHelpListPaging(Integer currentPage, Long memberNo) {
        int listCount = helpRepository.countMyHelpListByMemberNo(memberNo);
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        List<Help> helpList = helpRepository.findHelpListByMemberNo(memberNo, rowBounds);
        List<HelpReply> helpReplyList = helpRepository.findHelpReplyListByMemberNo(memberNo);

        for (Help help : helpList) {
            helpReplyList.stream().forEach(helpReply -> {
                if (help.getHelpNo() == helpReply.getHelpNo()) {
                    help.setReply(helpReply);
                }
            });
        }

        Map<String, Object> pageAndHelpList = new HashMap<>();
        pageAndHelpList.put("pageInfo", pageInfo);
        pageAndHelpList.put("helpList", helpList);

        return pageAndHelpList;
    }

    @Override
    public Map<String, Object> myOrderListPaging(Integer currentPage, Long memberNo) {
        int listCount = orderRepository.countMyOrderListByMemberNo(memberNo);
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 20);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        List<Order> orderList = orderRepository.findMyOrderListByMemberNo(memberNo, rowBounds);

        Map<String, Object> pageAndOrderList = new HashMap<>();
        pageAndOrderList.put("pageInfo", pageInfo);
        pageAndOrderList.put("orderList", orderList);
        return pageAndOrderList;
    }

}
