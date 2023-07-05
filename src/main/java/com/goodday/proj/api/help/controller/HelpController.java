package com.goodday.proj.api.help.controller;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.help.dto.HelpForm;
import com.goodday.proj.api.help.model.Help;
import com.goodday.proj.api.help.repository.HelpRepository;
import com.goodday.proj.api.help.service.HelpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cs/help")
public class HelpController {

    private final HelpService helpService;
    private final HelpRepository helpRepository;
    private final FileStore fileStore;

    /**
     * 1:1문의 작성
     * @param form
     * @param bindingResult
     * @throws IOException
     */
    @PostMapping
    public void writeHelp(@Valid @ModelAttribute HelpForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }
        int result = helpService.writeHelp(form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 1:1문의 삭제
     * @param helpNo
     */
    @DeleteMapping("/{helpNo}")
    public void deleteHelp(@PathVariable Long helpNo) {
        Help help = helpRepository.findHelpByHelpNo(helpNo);
        help.getImages().stream().forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));
        helpRepository.deleteFileByHelpNo(helpNo);
        helpRepository.deleteHelpReplyByHelpNo(helpNo);
        helpRepository.deleteHelpByHelpNo(helpNo);
    }

}
