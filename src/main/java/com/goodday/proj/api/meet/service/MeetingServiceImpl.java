package com.goodday.proj.api.meet.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.meet.dto.MeetingWriteForm;
import com.goodday.proj.api.meet.model.Meeting;
import com.goodday.proj.api.meet.repository.MeetingRepository;
import com.goodday.proj.api.member.dto.MemberSessionInfo;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.pagination.model.PageInfo;
import com.goodday.proj.pagination.Pagination;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    @Override
    public Map<String, Object> pageAndMeetingList(Integer currentPage) {
        int listCount = meetingRepository.countMeetingList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 10);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<Meeting> meetings = meetingRepository.findMeetingList(rowBounds);

        Map<String, Object> pageAndMeetingList = new HashMap<>();
        pageAndMeetingList.put("pageInfo", pageInfo);
        pageAndMeetingList.put("meetings", meetings);

        return pageAndMeetingList;
    }

    @Override
    public int writeMeet(MeetingWriteForm form) throws IOException {
        Meeting meeting =
                new Meeting(form.getMeetTitle(),
                            form.getMeetContent(),
                            form.getMeetAddress(),
                            form.getMeetTotal(),
                            form.getOpenKakao(),
                            form.getMemberNo(),
                            fileStore.storeFile(form.getThumbnail()));

        return meetingRepository.saveMeeting(meeting);
    }

    @Override
    public Meeting editMeetingView(Long meetNo) {
        postAuthorCheckByMeetNo(meetNo);
        return meetingRepository.findByMeetNo(meetNo);
    }

    @Override
    public List<MemberSessionInfo> meetingApplicationListView(Long meetNo) {
        return meetingRepository.findApplicationListByMeetNo(meetNo);
    }

    @Override
    public int removeApplication(Map<String, Long> cancel) {
        postAuthorCheckByMeetNo(cancel.get("meetNo"));
        return meetingRepository.deleteMeetingJoin(cancel);
    }

    @Override
    public int meetingEdit(Long meetNo, MeetingWriteForm form) throws IOException {
        postAuthorCheckByMeetNo(meetNo);

        Meeting originalMeeting = meetingRepository.findByMeetNo(meetNo);
        if (originalMeeting.getApplication() > form.getMeetTotal()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        // 새로운 파일이 들어왔을때 기존 삭제하고 DB FILESTORE 업데이트, MEETING 은 항상 업데이트
        if (!form.getThumbnail().getOriginalFilename().equals("")) {
            // 파일 삭제
            Meeting meeting = meetingRepository.findByMeetNo(meetNo);
            fileStore.deleteFile(meeting.getThumbnail().getStoreFileName());

            // 새로운 파일 등록
            UploadFile uploadFile = fileStore.storeFile(form.getThumbnail());

            // FILESTORE 업데이트
            Map<String, String> updateFile = new HashMap<>();
            updateFile.put("afterStoreFileName", meeting.getThumbnail().getStoreFileName());
            updateFile.put("uploadFileName", uploadFile.getUploadFileName());
            updateFile.put("storeFileName", uploadFile.getStoreFileName());
            meetingRepository.updateFileByStoreFileName(updateFile);
        }

        // MEETING 업데이트
        Map<String, Object> updateMeeting = new HashMap<>();
        updateMeeting.put("meetNo", meetNo);
        updateMeeting.put("memberNo", form.getMemberNo());
        updateMeeting.put("meetTitle", form.getMeetTitle());
        updateMeeting.put("meetContent", form.getMeetContent());
        updateMeeting.put("meetAddress", form.getMeetAddress());
        updateMeeting.put("meetTotal", form.getMeetTotal());
        updateMeeting.put("openKakao", form.getOpenKakao());

        return meetingRepository.updateMeeting(updateMeeting);
    }

    @Override
    public int deleteFileAndMeeting(Long meetNo) {
        postAuthorCheckByMeetNo(meetNo);

        Meeting meeting = meetingRepository.findByMeetNo(meetNo);
        fileStore.deleteFile(meeting.getThumbnail().getStoreFileName());
        meetingRepository.deleteFileByStoreFileName(meeting.getThumbnail().getStoreFileName());

        // 참가 신청도 삭제
        meetingRepository.deleteApplicationByMeetNo(meetNo);
        return meetingRepository.deleteByMeetNo(meetNo);
    }

    private void postAuthorCheckByMeetNo(Long meetNo) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        long sessionMemberNo = Long.parseLong(request.getHeader("memberNo"));

        if (sessionMemberNo != meetingRepository.findByMeetNo(meetNo).getMemberNo()
                && memberRepository.findSessionMemberByNo(sessionMemberNo).get().getAdmin().equals("N")) {
            throw new RuntimeException(ErrorConst.authError);
        }
    }

}
