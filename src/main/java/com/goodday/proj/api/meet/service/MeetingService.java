package com.goodday.proj.api.meet.service;

import java.util.Map;

public interface MeetingService {
    Map<String, Object> pageAndMeetingList(Integer currentPage);
}
