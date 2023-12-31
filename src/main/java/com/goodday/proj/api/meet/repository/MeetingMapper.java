package com.goodday.proj.api.meet.repository;

import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface MeetingMapper extends MeetingRepository{

    @Override
    int countMeetingList();

    @Override
    ArrayList<Meeting> findMeetingList(RowBounds rowBounds);

    @Override
    int saveMeeting(Meeting meeting);

    @Override
    Meeting findByMeetNo(Long meetNo);

    @Override
    int deleteFileByStoreFileName(String storeFileName);

    @Override
    int deleteApplicationByMeetNo(Long meetNo);

    @Override
    int deleteByMeetNo(Long meetNo);

    @Override
    int updateFileByStoreFileName(Map<String, String> updateFile);

    @Override
    int updateMeeting(Map<String, Object> updateMeeting);

    @Override
    int addMeetingJoin(Map<String, Long> join);

    @Override
    int deleteMeetingJoin(Map<String, Long> cancel);

    @Override
    List<MemberSessionInfo> findApplicationListByMeetNo(Long meetNo);

    @Override
    ArrayList<Meeting> findMeetingListByMemberNo(Long memberNo, RowBounds rowBounds);

    @Override
    int countMyMeetingListByMemberNo(Long memberNo);
}
