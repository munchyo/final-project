package com.goodday.proj.api.free.service;

import com.goodday.proj.api.free.dto.FreeBoardForm;
import com.goodday.proj.api.free.dto.FreeBoardReplyForm;
import com.goodday.proj.api.free.model.FreeBoard;

import java.io.IOException;
import java.util.Map;

public interface FreeBoardService {
    int writeFreeBoard(FreeBoardForm form) throws IOException;

    int editFreeBoard(Long freeNo, FreeBoardForm form) throws IOException;

    Map<String, Object> freeBoardListAndPaging(Integer currentPage);

    int writeReply(Long freeNo, FreeBoardReplyForm form);

    int editReply(Long freeNo, Long freeReNo, FreeBoardReplyForm form);

    FreeBoard freeBoardAndReplyView(Long freeNo);
}
