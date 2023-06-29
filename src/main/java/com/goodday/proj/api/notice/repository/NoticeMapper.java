package com.goodday.proj.api.notice.repository;

import com.goodday.proj.api.notice.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;

@Mapper
public interface NoticeMapper extends NoticeRepository {
    @Override
    int countNoticeList();

    @Override
    ArrayList<Notice> findNoticeList(RowBounds rowBounds);

    @Override
    int saveNotice(Notice notice);

    @Override
    Notice findByNoticeNo(Long noticeNo);
}
