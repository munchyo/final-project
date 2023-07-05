package com.goodday.proj.api.notice.controller;

import com.goodday.proj.annotation.AuthChecker;
import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.notice.dto.NoticeForm;
import com.goodday.proj.api.notice.model.Notice;
import com.goodday.proj.api.notice.repository.NoticeRepository;
import com.goodday.proj.api.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    /**
     * 공지사항 목록
     *
     * @param currentPage
     * @return Map
     */
    @GetMapping
    public Map<String, Object> notice(@RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return noticeService.noticeListAndPagination(currentPage);
    }

    /**
     * 공지사항 작성
     *
     * @param form
     * @param bindingResult
     * @throws IOException
     */
    @AuthChecker
    @PostMapping("/write")
    public void writeNotice(@Valid @ModelAttribute NoticeForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }

        int result = noticeService.writeNotice(form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 공지사항 상세보기
     *
     * @param noticeNo
     * @return Notice
     */
    @GetMapping("/{noticeNo}")
    public Notice noticeView(@PathVariable Long noticeNo) {
        return noticeRepository.findByNoticeNo(noticeNo);
    }

    /**
     * 공지사항 수정창
     *
     * @param noticeNo
     * @return
     */
    @AuthChecker
    @GetMapping("/{noticeNo}/edit")
    public Notice editNoticeView(@PathVariable Long noticeNo) {
        return noticeRepository.findByNoticeNo(noticeNo);
    }

    /**
     * 공지사항 수정
     *
     * @param noticeNo
     * @param form
     * @param bindingResult
     */
    @AuthChecker
    @PostMapping("/{noticeNo}/edit")
    public void editNotice(@PathVariable Long noticeNo, @Valid @ModelAttribute NoticeForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        int result = noticeService.editNotice(noticeNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 공지사항 삭제
     * @param noticeNo
     */
    @AuthChecker
    @DeleteMapping("/{noticeNo}")
    public void deleteNotice(@PathVariable Long noticeNo) {
        Notice notice = noticeRepository.findByNoticeNo(noticeNo);
        notice.getImages().stream().forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));

        noticeRepository.deleteNoticeByNoticeNo(noticeNo);
        noticeRepository.deleteFileByNoticeNo(noticeNo);
    }
}
