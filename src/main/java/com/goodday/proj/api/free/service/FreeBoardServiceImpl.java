package com.goodday.proj.api.free.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.free.dto.FreeBoardForm;
import com.goodday.proj.api.free.dto.FreeBoardReplyForm;
import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.free.model.FreeBoardReply;
import com.goodday.proj.api.free.repository.FreeBoardRepository;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.pagination.Pagination;
import com.goodday.proj.pagination.model.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {

    private final FreeBoardRepository freeBoardRepository;
    private final FileStore fileStore;
    private final MemberRepository memberRepository;

    @Override
    public int writeFreeBoard(FreeBoardForm form) throws IOException {
        FreeBoard freeBoard = new FreeBoard();
        if (!form.getFile().getOriginalFilename().equals("")) {
            UploadFile uploadFile = fileStore.storeFile(form.getFile());
            freeBoard.setFile(uploadFile);
        }
        if (form.getImages().stream().filter(file -> !file.getOriginalFilename().equals("")).findFirst().isPresent()) {
            List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImages());
            freeBoard.setImages(uploadFiles);
        }
        freeBoard.setFreeTitle(form.getFreeTitle());
        freeBoard.setFreeContent(form.getFreeContent());
        freeBoard.setMemberNo(form.getMemberNo());
        return freeBoardRepository.saveFreeBoard(freeBoard);
    }

    @Override
    public int editFreeBoard(Long freeNo, FreeBoardForm form) throws IOException {
        postAuthorCheckByFreeNo(freeNo);

        int result = 0;
        if (!form.getFile().getOriginalFilename().equals("")) {
            UploadFile uploadFile = fileStore.storeFile(form.getFile());
            Map<String, Object> saveFile = new HashMap<>();
            saveFile.put("freeNo", freeNo);
            saveFile.put("file", uploadFile);
            result += freeBoardRepository.saveFile(saveFile);
        }

        if (form.getImages().stream().filter(file -> !file.getOriginalFilename().equals("")).findFirst().isPresent()) {
            List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImages());
            Map<String, Object> saveImages = new HashMap<>();
            saveImages.put("freeNo", freeNo);
            saveImages.put("images", uploadFiles);
            result += freeBoardRepository.saveImages(saveImages);
        }
        FreeBoard freeBoard = new FreeBoard(freeNo, form.getFreeTitle(), form.getFreeContent());
        result += freeBoardRepository.updateFreeBoard(freeBoard);
        return result;
    }

    private void postAuthorCheckByFreeNo(Long freeNo) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        long sessionMemberNo = Long.parseLong(request.getHeader("memberNo"));

        if (sessionMemberNo != freeBoardRepository.findByFreeNo(freeNo).getMemberNo()
                && memberRepository.findSessionMemberByNo(sessionMemberNo).get().getAdmin().equals("N")) {
            throw new RuntimeException(ErrorConst.authError);
        }
    }

    @Override
    public Map<String, Object> freeBoardListAndPaging(Integer currentPage) {
        int listCount = freeBoardRepository.countFreeBoardList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 20);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<FreeBoard> freeBoards = freeBoardRepository.findFreeBoardList(rowBounds);

        Map<String, Object> pageAndFreeBoardList = new HashMap<>();
        pageAndFreeBoardList.put("pageInfo", pageInfo);
        pageAndFreeBoardList.put("freeBoards", freeBoards);
        return pageAndFreeBoardList;
    }

    @Override
    public FreeBoard freeBoardAndReplyView(Long freeNo) {
        FreeBoard freeBoard = freeBoardRepository.findByFreeNo(freeNo);
        List<FreeBoardReply> freeBoardReplyList = freeBoardRepository.findReplyListByFreeNo(freeNo);
        freeBoard.setReplies(freeBoardReplyList);
        return freeBoard;
    }

    @Override
    public int writeReply(Long freeNo, FreeBoardReplyForm form) {
        FreeBoardReply reply = new FreeBoardReply(form.getMemberNo(), form.getFreeReContent(), freeNo);
        return freeBoardRepository.saveReply(reply);
    }

    @Override
    public int editReply(Long freeNo, Long freeReNo, FreeBoardReplyForm form) {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        long sessionMemberNo = Long.parseLong(request.getHeader("memberNo"));

        if (sessionMemberNo != freeBoardRepository.findReplyByFreeReNo(freeReNo).getMemberNo()
                && memberRepository.findSessionMemberByNo(sessionMemberNo).get().getAdmin().equals("N")) {
            throw new RuntimeException(ErrorConst.authError);
        }

        Map<String, Object> edit = new HashMap<>();
        edit.put("freeReNo", freeReNo);
        edit.put("freeReContent", form.getFreeReContent());
        return freeBoardRepository.updateReply(edit);
    }
}
