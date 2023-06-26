package com.goodday.proj.api.meet.controller;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.meet.service.MeetingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param currentPage
     * @return
     */
    @GetMapping
    public Map<String, Object> meetingList(@RequestParam(required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }

        return meetingService.pageAndMeetingList(currentPage);
    }

}
