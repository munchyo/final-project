package com.goodday.proj.api.meet.service;

import com.goodday.proj.api.meet.dto.MeetingWriteForm;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.member.dto.MemberSessionInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MeetingService {
    Map<String, Object> pageAndMeetingList(Integer currentPage);

    int writeMeet(MeetingWriteForm form) throws IOException;

    int meetingEdit(Long meetNo, MeetingWriteForm form) throws IOException;

    int deleteFileAndMeeting(Long meetNo);

    Meeting editMeetingView(Long meetNo);

    List<MemberSessionInfo> meetingApplicationListView(Long meetNo);

    int removeApplication(Map<String, Long> cancel);
}
