package com.goodday.proj.api.meet.repository;

import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MeetingRepository {
    int countMeetingList();

    ArrayList<Meeting> selectMeetingList(RowBounds rowBounds);

    int saveMeeting(Meeting meeting);

    Meeting findByMeetNo(Long meetNo);

    int deleteFileByStoreFileName(String storeFileName);

    int deleteApplicationByMeetNo(Long meetNo);

    int deleteByMeetNo(Long meetNo);

    int updateFileByStoreFileName(Map<String, String> updateFile);

    int updateMeeting(Map<String, Object> updateMeeting);

    int addMeetingJoin(Map<String, Long> join);

    int deleteMeetingJoin(Map<String, Long> cancel);

    List<MemberSessionInfo> findApplicationListByMeetNo(Long meetNo);
}
