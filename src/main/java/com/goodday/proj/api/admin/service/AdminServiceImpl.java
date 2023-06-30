package com.goodday.proj.api.admin.service;

import com.goodday.proj.api.admin.repository.AdminMapper;
import com.goodday.proj.api.admin.repository.AdminRepository;
import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.help.repository.HelpRepository;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.pagination.model.PageInfo;
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
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Map<String, Object> allHelpView(Integer currentPage) {
        int listCount = adminRepository.countHelpList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());

        List<Help> helpList = adminRepository.findHelpList(rowBounds);
        List<HelpReply> helpReplyList = adminRepository.findHelpReplyList();

        for (Help help : helpList) {
            helpReplyList.stream().forEach(helpReply -> {
                if (help.getHelpNo() == helpReply.getHelpNo()) {
                    help.setReply(helpReply);
                }
            });
        }

        Map<String, Object> helpListAndPaging = new HashMap<>();
        helpListAndPaging.put("pageInfo", pageInfo);
        helpListAndPaging.put("helpList", helpList);
        return helpListAndPaging;
    }
}
