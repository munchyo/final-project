package com.goodday.proj.api.admin.controller;

import com.goodday.proj.api.admin.repository.AdminRepository;
import com.goodday.proj.api.admin.service.AdminService;
import com.goodday.proj.api.constant.ErrorConst;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.help.repository.HelpRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final HelpRepository helpRepository;
    private final AdminRepository adminRepository;

    /**
     * 모든문의보기 TODO 필터넣자
     * @param currentPage
     * @return
     */
    @GetMapping("/help")
    public Map<String, Object> selectAllHelps(@RequestParam(value = "page", required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }

        return adminService.allHelpView(currentPage);
    }

    /**
     * 문의 답변하기
     * @param helpReply
     * @param bindingResult
     */
    @PostMapping("/help/reply")
    public void writeHelpReply(@Valid @ModelAttribute HelpReply helpReply, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.nullError);
        }
        adminRepository.saveHelpReply(helpReply);
    }
}
