package com.goodday.proj.api.help.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HelpRepository {
    int saveHelpAndImages(Help help);

    int saveHelp(Help help);

    Help findHelpByHelpNo(Long helpNo);

    int deleteFileByHelpNo(Long helpNo);

    int deleteHelpByHelpNo(Long helpNo);

    int countMyHelpListByMemberNo(Long memberNo);

    List<Help> findHelpListByMemberNo(Long memberNo, RowBounds rowBounds);

    List<HelpReply> findHelpReplyListByMemberNo(Long memberNo);

    int deleteHelpReplyByHelpNo(Long helpNo);
}
