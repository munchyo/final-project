package com.goodday.proj.api.notice.service;

import com.goodday.proj.api.notice.dto.NoticeForm;

import java.io.IOException;
import java.util.Map;

public interface NoticeService {
    Map<String, Object> noticeListAndPagination(Integer currentPage);

    int writeNotice(NoticeForm form) throws IOException;

    int editNotice(Long noticeNo, NoticeForm form) throws IOException;
}
