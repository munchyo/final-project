package com.goodday.proj.api.meet.controller;

import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.meet.dto.MeetingWriteForm;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.meet.service.MeetingService;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/meet")
public class MeetingController {

    private final MeetingService meetingService;
    private final MeetingRepository meetingRepository;

    /**
     * 모임 목록, 페이징
     *
     * @param currentPage
     * @return
     */
    @GetMapping
    public Map<String, Object> meetingList(@RequestParam(required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return meetingService.pageAndMeetingList(currentPage);
    }

    /**
     * 모임 글 작성
     *
     * @param form
     * @throws IOException
     */
    @PostMapping("/write")
    public void meetingWrite(@Valid @ModelAttribute MeetingWriteForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        int result = meetingService.writeMeet(form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 모임 이동
     *
     * @param meetNo
     * @return Meeting
     */
    @GetMapping("/{meetNo}")
    public Meeting meetingView(@PathVariable Long meetNo) {
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
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        Meeting originalMeeting = meetingRepository.findByMeetNo(meetNo);
        if (originalMeeting.getApplication() > form.getMeetTotal()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        if (originalMeeting.getMemberNo() != form.getMemberNo()) {
            throw new IllegalArgumentException(ErrorConst.authError);
        }

        int result = meetingService.meetingEdit(meetNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 모임, 모임신청, 썸네일 삭제
     *
     * @param meetNo
     */
    @DeleteMapping("/{meetNo}")
    public void meetingDelete(@PathVariable Long meetNo) {
        int result = meetingService.deleteFileAndMeeting(meetNo);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }

    /**
     * 모임신청
     *
     * @param meetNo
     * @param memberNo
     */
    @PostMapping("/{meetNo}/join")
    public void meetingJoin(@PathVariable Long meetNo, @RequestParam Long memberNo) {
        Meeting meeting = meetingRepository.findByMeetNo(meetNo);
        if (meeting.getMemberNo() == memberNo) {
            throw new RuntimeException(ErrorConst.authError);
        }
        if (meeting.getMeetTotal() == meeting.getApplication()) {
            throw new RuntimeException("정원이 가득 찼습니다.");
        }

        Map<String, Long> join = new HashMap<>();
        join.put("meetNo", meetNo);
        join.put("memberNo", memberNo);
        int result = meetingRepository.addMeetingJoin(join);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 모임 신청 취소
     *
     * @param meetNo
     * @param memberNo
     */
    @DeleteMapping("/{meetNo}/join")
    public void meetingJoinCancel(@PathVariable Long meetNo, @RequestParam Long memberNo) {
        Map<String, Long> cancel = new HashMap<>();
        cancel.put("meetNo", meetNo);
        cancel.put("memberNo", memberNo);
        int result = meetingRepository.deleteMeetingJoin(cancel);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }

    /**
     * 모임 참가자 리스트
     * @param meetNo
     * @param memberNo
     * @return
     */
    @PostMapping("/{meetNo}/application/{memberNo}")
    public List<MemberSessionInfo> meetingApplicationList(@PathVariable Long meetNo, @PathVariable Long memberNo) {
        if (meetingRepository.findByMeetNo(meetNo).getMemberNo() != memberNo) {
            throw new RuntimeException(ErrorConst.authError);
        }
        return meetingRepository.findApplicationListByMeetNo(meetNo);
    }

    /**
     * 모임 참가자 추방
     * @param meetNo
     * @param memberNo
     */
    @DeleteMapping("/{meetNo}/application/{memberNo}")
    public void removeMeetingApplication(@PathVariable Long meetNo, @PathVariable Long memberNo) {
        if (meetingRepository.findByMeetNo(meetNo).getMemberNo() != memberNo) {
            throw new RuntimeException(ErrorConst.authError);
        }
        Map<String, Long> cancel = new HashMap<>();
        cancel.put("meetNo", meetNo);
        cancel.put("memberNo", memberNo);
        int result = meetingRepository.deleteMeetingJoin(cancel);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }
}
