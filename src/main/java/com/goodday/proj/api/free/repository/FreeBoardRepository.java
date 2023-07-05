package com.goodday.proj.api.free.repository;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.free.model.FreeBoardReply;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FreeBoardRepository {
    int saveFreeBoard(FreeBoard freeBoard);

    int saveFile(Map<String, Object> saveFile);

    int saveImages(Map<String, Object> saveImages);

    int updateFreeBoard(FreeBoard freeBoard);

    FreeBoard findByFreeNo(Long freeNo);

    int deleteFreeBoardByFreeNo(Long freeNo);

    int countFreeBoardList();

    int saveReply(FreeBoardReply reply);

    int updateReply(Map<String, Object> edit);

    int deleteReplyByFreeReNo(Long freeReNo);

    ArrayList<FreeBoard> findFreeBoardList(RowBounds rowBounds);

    List<FreeBoardReply> findReplyListByFreeNo(Long freeNo);

    FreeBoardReply findReplyByFreeReNo(Long freeReNo);
}
