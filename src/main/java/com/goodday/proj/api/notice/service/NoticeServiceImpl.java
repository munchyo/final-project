package com.goodday.proj.api.notice.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.notice.dto.NoticeForm;
import com.goodday.proj.api.notice.model.Notice;
import com.goodday.proj.api.notice.repository.NoticeRepository;
import com.goodday.proj.pagination.Pagination;
import com.goodday.proj.pagination.model.PageInfo;
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

    @Override
    public int editNotice(Long noticeNo, NoticeForm form) throws IOException {
        int result = 0;
        if (form.getImages().stream()
                .filter(file -> !file.getOriginalFilename().equals("")).findAny().isPresent()) {

            List<UploadFile> images = fileStore.storeFiles(form.getImages());
            Map imageMap = new HashMap();
            imageMap.put("images", images);
            imageMap.put("noticeNo", noticeNo);
            result += noticeRepository.saveImages(imageMap);
        }

        Notice notice = new Notice(noticeNo, form.getNoticeTitle(), form.getNoticeContent());
        result += noticeRepository.updateNotice(notice);

        return result;
    }

}
