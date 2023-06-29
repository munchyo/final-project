package com.goodday.proj.api.notice.repository;

import com.goodday.proj.api.notice.model.Notice;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;

public interface NoticeRepository {
    int countNoticeList();

    ArrayList<Notice> findNoticeList(RowBounds rowBounds);

    int saveNotice(Notice notice);

    Notice findByNoticeNo(Long noticeNo);
}
