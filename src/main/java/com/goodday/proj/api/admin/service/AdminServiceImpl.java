package com.goodday.proj.api.admin.service;

import com.goodday.proj.api.admin.repository.AdminRepository;
import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.member.model.Member;
import com.goodday.proj.pagination.Pagination;
import com.goodday.proj.pagination.model.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Map<String, Object> allHelpView(Integer currentPage, String reply, String type) {
        List<Help> helpList = null;
        List<HelpReply> helpReplyList = null;
        if (!type.equals("ALL") && !type.isEmpty() && type != null) {
            int listCount = adminRepository.countHelpListByType(type);
            PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

            RowBounds rowBounds = new RowBounds((pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit(), pageInfo.getBoardLimit());

            helpList = adminRepository.findHelpListByType(type, rowBounds);
            helpReplyList = adminRepository.findHelpReplyList();

            matchHelpAndReplyByNo(helpList, helpReplyList);

            return getHelpListAndPaging(pageInfo, helpList, reply);
        }

        int listCount = adminRepository.countHelpList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

        RowBounds rowBounds = new RowBounds((pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit(),
                pageInfo.getBoardLimit());

        helpList = adminRepository.findHelpList(rowBounds);
        helpReplyList = adminRepository.findHelpReplyList();

        matchHelpAndReplyByNo(helpList, helpReplyList);



        return getHelpListAndPaging(pageInfo, helpList, reply);
    }

    private static void matchHelpAndReplyByNo(List<Help> helpList, List<HelpReply> helpReplyList) {
        for (Help help : helpList) {
            helpReplyList.stream().forEach(helpReply -> {
                if (help.getHelpNo() == helpReply.getHelpNo()) {
                    help.setReply(helpReply);
                }
            });
        }
    }

    @NotNull
    private static Map<String, Object> getHelpListAndPaging(PageInfo pageInfo, List<Help> helpList, String reply) {
        Map<String, Object> helpListAndPaging = new HashMap<>();
        if (reply.equals("Y")) {
            helpList = helpList.stream().filter(help -> help.getReply() != null).toList();
            helpListAndPaging.put("helpList", helpList);
        } else if (reply.equals("N")) {
            helpList = helpList.stream().filter(help -> help.getReply() == null).toList();
            helpListAndPaging.put("helpList", helpList);
        } else {
            helpListAndPaging.put("helpList", helpList);
        }

        helpListAndPaging.put("pageInfo", pageInfo);
        return helpListAndPaging;
    }

    @Override
    public Map<String, Object> pagingAndMemberList(Integer currentPage, String searchId, String searchNickname) {
        Map<String, Object> membersAndPageInfo = new HashMap<>();
        PageInfo pageInfo = null;
        if (searchId.equals("ALL") && searchNickname.equals("ALL")) {
            int listCount = adminRepository.countMemberList();
            pageInfo = Pagination.getPageInfo(currentPage, listCount, 30);

            RowBounds rowBounds = new RowBounds((pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit(),
                    pageInfo.getBoardLimit());
            List<Member> members = adminRepository.findMemberList(rowBounds);

            membersAndPageInfo.put("pageInfo", pageInfo);
            membersAndPageInfo.put("members", members);
        }
        if (!searchId.equals("ALL") && searchNickname.equals("ALL")) {
            int listCount = adminRepository.countMemberListById(searchId);
            pageInfo = Pagination.getPageInfo(currentPage, listCount, 30);

            RowBounds rowBounds = new RowBounds((pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit(),
                    pageInfo.getBoardLimit());
            List<Member> members = adminRepository.findMemberListById(searchId, rowBounds);

            membersAndPageInfo.put("pageInfo", pageInfo);
            membersAndPageInfo.put("members", members);
        }
        if (searchId.equals("ALL") && !searchNickname.equals("ALL")) {
            int listCount = adminRepository.countMemberListByNickname(searchNickname);
            pageInfo = Pagination.getPageInfo(currentPage, listCount, 30);

            RowBounds rowBounds = new RowBounds((pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit(),
                    pageInfo.getBoardLimit());
            List<Member> members = adminRepository.findMemberListByNickname(searchNickname, rowBounds);

            membersAndPageInfo.put("pageInfo", pageInfo);
            membersAndPageInfo.put("members", members);
        }
        if (!searchId.equals("ALL") && !searchNickname.equals("ALL")) {
            throw new IllegalArgumentException("한 번에 두개의 조건으로 검색이 불가합니다.");
        }

        return membersAndPageInfo;
    }

}
