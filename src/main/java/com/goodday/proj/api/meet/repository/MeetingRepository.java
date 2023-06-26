package com.goodday.proj.api.meet.repository;

import com.goodday.proj.api.meet.model.Meeting;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;

public interface MeetingRepository {
    int countMeetingList();

    ArrayList<Meeting> selectMeetingList(RowBounds rowBounds);
}
