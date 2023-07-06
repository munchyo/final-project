package com.goodday.proj.api.admin.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.member.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper extends AdminRepository {
    @Override
    int saveHelpReply(HelpReply helpReply);

    @Override
    int countHelpList();

    @Override
    List<Help> findHelpList(RowBounds rowBounds);

    @Override
    List<HelpReply> findHelpReplyList();

    @Override
    int countHelpListByType(String type);

    @Override
    List<Help> findHelpListByType(String type, RowBounds rowBounds);

    @Override
    int countMemberList();

    @Override
    List<Member> findMemberList(RowBounds rowBounds);

    @Override
    int countMemberListById(String searchId);

    @Override
    List<Member> findMemberListById(String searchId, RowBounds rowBounds);

    @Override
    int countMemberListByNickname(String searchNickname);

    @Override
    List<Member> findMemberListByNickname(String searchNickname, RowBounds rowBounds);

    @Override
    int updateMemberStatus(Map<String, Object> update);

    @Override
    int updateMemberRole(Map<String, Object> update);

    @Override
    List<Integer> countEnrollMember30Days();

    @Override
    List<String> enrollDate30Days();
}
