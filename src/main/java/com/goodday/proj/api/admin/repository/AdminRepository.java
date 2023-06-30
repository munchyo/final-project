package com.goodday.proj.api.admin.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AdminRepository {
    int saveHelpReply(HelpReply helpReply);

    int countHelpList();

    List<Help> findHelpList(RowBounds rowBounds);

    List<HelpReply> findHelpReplyList();

}
