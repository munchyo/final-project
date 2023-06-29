package com.goodday.proj.api.notice.repository;

import com.goodday.proj.api.notice.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Map;

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

    @Override
    int saveImages(Map imageMap);

    @Override
    int updateNotice(Notice notice);

    @Override
    int deleteNoticeByNoticeNo(Long noticeNo);

    @Override
    int deleteFileByNoticeNo(Long noticeNo);
}
