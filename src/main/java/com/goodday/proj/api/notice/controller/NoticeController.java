package com.goodday.proj.api.notice.controller;

import com.goodday.proj.api.constant.ErrorConst;
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
    @PostMapping("/write")
    public void writeNotice(@Valid @ModelAttribute NoticeForm form, BindingResult bindingResult) throws IOException {
        if (memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getAdmin().equals("N")) {
            throw new RuntimeException(ErrorConst.authError);
        }
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
     * @param noticeNo
     * @return
     */
    @GetMapping("/{noticeNo}/edit")
    public Notice editNoticeView(@PathVariable Long noticeNo) {
        return noticeRepository.findByNoticeNo(noticeNo);
    }
}
