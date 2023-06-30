package com.goodday.proj.api.admin.repository;

import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.model.HelpReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

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
}
