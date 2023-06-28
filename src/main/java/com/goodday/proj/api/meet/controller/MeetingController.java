package com.goodday.proj.api.meet.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.meet.dto.MeetingWriteForm;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.meet.service.MeetingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meet")
public class MeetingController {

    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;
    private final FileStore fileStore;

    /**
     * 모임 목록, 페이징
     *
     * @param currentPage
     * @return
     */
    @GetMapping
    public Map<String, Object> meetingList(@RequestParam(required = false) Integer currentPage) {
        // 참가 수도 추가하자
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }
        return meetingService.pageAndMeetingList(currentPage);
    }

    /**
     * 모임 글 작성
     * @param form
     * @throws IOException
     */
    @PostMapping("/write")
    public void meetingWrite(@Valid @ModelAttribute MeetingWriteForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        meetingService.writeMeet(form);
    }

    /**
     * 모임 이동
     *
     * @param meetNo
     * @return Meeting
     */
    @GetMapping("/{meetNo}")
    public Meeting meetingView(@PathVariable Long meetNo) {
        // 참가 수도 추가하자
        return meetingRepository.findByMeetNo(meetNo);
    }

    /**
     * 모임 수정 창
     *
     * @param meetNo
     * @return Meeting
     */
    @GetMapping("/{meetNo}/edit")
    public Meeting meetingEditView(@PathVariable Long meetNo) {
        // 참가 수도 추가하자
        return meetingRepository.findByMeetNo(meetNo);
    }

    /**
     * 모임 수정
     *
     * @param meetNo
     * @param form
     * @throws IOException
     */
    @PostMapping("/{meetNo}/edit")
    public void meetingEdit(@PathVariable Long meetNo, @Valid @ModelAttribute MeetingWriteForm form, BindingResult bindingResult) throws IOException {
        // 총 정원을 참가신청보다 적게 하려하면 오류내야함
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        meetingService.meetingEdit(meetNo, form);
    }

    /**
     * 모임, 모임신청, 썸네일 삭제
     * @param meetNo
     */
    @DeleteMapping("/{meetNo}")
    public void meetingDelete(@PathVariable Long meetNo) {
        meetingService.deleteFileAndMeeting(meetNo);
    }


}
