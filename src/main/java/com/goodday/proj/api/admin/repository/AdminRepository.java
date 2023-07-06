package com.goodday.proj.api.admin.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.member.model.Member;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface AdminRepository {
    int saveHelpReply(HelpReply helpReply);

    int countHelpList();

    List<Help> findHelpList(RowBounds rowBounds);

    List<HelpReply> findHelpReplyList();

    int countHelpListByType(String type);

    List<Help> findHelpListByType(String type, RowBounds rowBounds);

    int countMemberList();

    List<Member> findMemberList(RowBounds rowBounds);

    int countMemberListById(String searchId);

    List<Member> findMemberListById(String searchId, RowBounds rowBounds);

    int countMemberListByNickname(String searchNickname);

    List<Member> findMemberListByNickname(String searchNickname, RowBounds rowBounds);

    int updateMemberStatus(Map<String, Object> update);

    int updateMemberRole(Map<String, Object> update);

    List<Integer> countEnrollMember30Days();

    List<String> enrollDate30Days();
}
