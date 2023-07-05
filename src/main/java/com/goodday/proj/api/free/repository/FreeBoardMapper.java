package com.goodday.proj.api.free.repository;

import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.free.model.FreeBoardReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface FreeBoardMapper extends FreeBoardRepository {
    @Override
    int saveFreeBoard(FreeBoard freeBoard);

    @Override
    int saveFile(Map<String, Object> saveFile);

    @Override
    int saveImages(Map<String, Object> saveImages);

    @Override
    int updateFreeBoard(FreeBoard freeBoard);

    @Override
    FreeBoard findByFreeNo(Long freeNo);

    @Override
    int deleteFreeBoardByFreeNo(Long freeNo);

    @Override
    int countFreeBoardList();

    @Override
    int deleteReplyByFreeReNo(Long freeReNo);

    @Override
    ArrayList<FreeBoard> findFreeBoardList(RowBounds rowBounds);

    @Override
    List<FreeBoardReply> findReplyListByFreeNo(Long freeNo);

    @Override
    int saveReply(FreeBoardReply reply);

    @Override
    int updateReply(Map<String, Object> edit);

    @Override
    FreeBoardReply findReplyByFreeReNo(Long freeReNo);
}
