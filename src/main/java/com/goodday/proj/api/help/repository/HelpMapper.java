package com.goodday.proj.api.help.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface HelpMapper extends HelpRepository {
    @Override
    int saveHelpAndImages(Help help);

    @Override
    int saveHelp(Help help);

    @Override
    Help findHelpByHelpNo(Long helpNo);

    @Override
    int deleteFileByHelpNo(Long helpNo);

    @Override
    int deleteHelpByHelpNo(Long helpNo);

    @Override
    int countMyHelpListByMemberNo(Long memberNo);

    @Override
    List<Help> findHelpListByMemberNo(Long memberNo, RowBounds rowBounds);

    @Override
    List<HelpReply> findHelpReplyListByMemberNo(Long memberNo);

    @Override
    int deleteHelpReplyByHelpNo(Long helpNo);
}
