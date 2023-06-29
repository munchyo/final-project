package com.goodday.proj.api.notice.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.notice.dto.NoticeForm;
import com.goodday.proj.api.notice.model.Notice;
import com.goodday.proj.api.notice.repository.NoticeRepository;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.pagination.model.PageInfo;
import com.goodday.proj.api.shop.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final FileStore fileStore;

    @Override
    public Map<String, Object> noticeListAndPagination(Integer currentPage) {
        int listCount = noticeRepository.countNoticeList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 20);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<Notice> noticeList = noticeRepository.findNoticeList(rowBounds);

        Map<String, Object> noticeListAndPagination = new HashMap<>();
        noticeListAndPagination.put("pageInfo", pageInfo);
        noticeListAndPagination.put("noticeList", noticeList);

        return noticeListAndPagination;
    }

    @Override
    public int writeNotice(NoticeForm form) throws IOException {
        Notice notice = new Notice(form.getNoticeTitle(), form.getNoticeContent(),
                form.getMemberNo(), fileStore.storeFiles(form.getImages()));
        return noticeRepository.saveNotice(notice);
    }

}
