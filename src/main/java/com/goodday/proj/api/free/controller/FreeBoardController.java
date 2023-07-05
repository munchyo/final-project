package com.goodday.proj.api.free.controller;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.repository.FileRepository;
import com.goodday.proj.api.free.dto.FreeBoardForm;
import com.goodday.proj.api.free.dto.FreeBoardReplyForm;
import com.goodday.proj.api.free.model.FreeBoard;
import com.goodday.proj.api.free.repository.FreeBoardRepository;
import com.goodday.proj.api.free.service.FreeBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/free")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;
    private final FreeBoardRepository freeBoardRepository;
    private final FileStore fileStore;
    private final FileRepository fileRepository;

    /**
     * 게시글 목록 보기
     * @param currentPage
     * @return Map
     */
    @GetMapping
    public Map<String, Object> freeBoardList(@RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        return freeBoardService.freeBoardListAndPaging(currentPage);
    }

    /**
     * 게시판 상세보기
     * @param freeNo
     * @return
     */
    @GetMapping("/{freeNo}")
    public FreeBoard freeBoardView(@PathVariable Long freeNo) {
        return freeBoardService.freeBoardAndReplyView(freeNo);
    }

    /**
     * 자유게시판 작성
     *
     * @param form
     * @param bindingResult
     * @throws IOException
     */
    @PostMapping
    public void writeFreeBoard(@Valid @ModelAttribute FreeBoardForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        int result = freeBoardService.writeFreeBoard(form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 자유게시판 수정
     *
     * @param freeNo
     * @param form
     * @param bindingResult
     * @throws IOException
     */
    @PostMapping("/{freeNo}")
    public void editFreeBoard(@PathVariable Long freeNo, @Valid @ModelAttribute FreeBoardForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        int result = freeBoardService.editFreeBoard(freeNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 자유게시판 삭제
     *
     * @param freeNo
     */
    @DeleteMapping("/{freeNo}")
    public void deleteFreeBoard(@PathVariable Long freeNo) {
        FreeBoard freeBoard = freeBoardRepository.findByFreeNo(freeNo);
        if (freeBoard.getFile() != null) {
            fileStore.deleteFile(freeBoard.getFile().getStoreFileName());
            fileRepository.deleteFile(freeBoard.getFile().getStoreFileName());
        }
        if (freeBoard.getImages().stream().filter(uploadFile -> uploadFile != null).findFirst().isPresent()) {
            freeBoard.getImages().stream().forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));
            freeBoard.getImages().stream().forEach(uploadFile -> fileRepository.deleteFile(uploadFile.getStoreFileName()));
        }

        int result = freeBoardRepository.deleteFreeBoardByFreeNo(freeNo);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }

    /**
     * 댓글 작성
     *
     * @param freeNo
     * @param form
     * @param bindingResult
     */
    @PostMapping("/{freeNo}/reply")
    public void writeReply(@PathVariable Long freeNo, @Valid @ModelAttribute FreeBoardReplyForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }
        int result = freeBoardService.writeReply(freeNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 댓글 수정
     *
     * @param freeNo
     * @param freeReNo
     * @param form
     * @param bindingResult
     */
    @PostMapping("/{freeNo}/reply/{freeReNo}")
    public void editReply(@PathVariable Long freeNo, @PathVariable Long freeReNo,
                          @Valid @ModelAttribute FreeBoardReplyForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }
        int result = freeBoardService.editReply(freeNo, freeReNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 댓글 삭제
     * @param freeNo
     * @param freeReNo
     */
    @DeleteMapping("/{freeNo}/reply/{freeReNo}")
    public void deleteReply(@PathVariable Long freeNo, @PathVariable Long freeReNo) {
        freeBoardRepository.deleteReplyByFreeReNo(freeReNo);
    }
}
