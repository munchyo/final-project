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
     * 모든문의보기
     * 답변유 -> 문의유형
     * 답변무 -> 문의유형
     * @param currentPage
     * @return
     */
    @GetMapping("/help")
    public Map<String, Object> selectAllHelps(@RequestParam(value = "page", required = false) Integer currentPage,
                                              @RequestParam(required = false) String reply,
                                              @RequestParam(value = "type", required = false) String type) {
//        reply -> Y, N, ALL
//        type -> 문의유형 or ALL
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (reply == null || !reply.equals("Y") && !reply.equals("N")) {
            reply = "ALL";
        }
        if (type == null) {
            type = "ALL";
        }

        return adminService.allHelpView(currentPage, reply, type);
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
        int result = adminRepository.saveHelpReply(helpReply);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }
    
    // TODO 회원관리, 통계
}
