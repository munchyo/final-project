package com.goodday.proj.api.meet.repository;

import com.goodday.proj.api.meet.model.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;

@Mapper
public interface MeetingMapper extends MeetingRepository{

    @Override
    int countMeetingList();

    @Override
    ArrayList<Meeting> selectMeetingList(RowBounds rowBounds);
}
