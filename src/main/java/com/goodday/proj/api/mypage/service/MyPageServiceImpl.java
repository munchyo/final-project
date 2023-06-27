package com.goodday.proj.api.mypage.service;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.mypage.repository.MyPageRepository;
import com.goodday.proj.api.pagination.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;

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

}
