package com.goodday.proj.api.admin.controller;

import com.goodday.proj.api.admin.repository.AdminRepository;
import com.goodday.proj.api.admin.service.AdminService;
import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.help.model.HelpReply;
import com.goodday.proj.api.help.repository.HelpRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
     *
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
     *
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

    /**
     * 모든 회원 보기
     *
     * @param currentPage
     * @param searchId
     * @param searchNickname
     * @return
     */
    @GetMapping("/member")
    public Map<String, Object> manageMemberView(@RequestParam(value = "page", required = false) Integer currentPage,
                                                @RequestParam(value = "id", required = false) String searchId,
                                                @RequestParam(value = "nickname", required = false) String searchNickname) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (searchId == null) {
            searchId = "ALL";
        }
        if (searchNickname == null) {
            searchNickname = "ALL";
        }
        return adminService.pagingAndMemberList(currentPage, searchId, searchNickname);
    }

    /**
     * 회원 상태 수정
     *
     * @param memberNo
     * @param status
     */
    @PostMapping("/member/{memberNo}/status")
    public void changeMemberStatus(@PathVariable Long memberNo, @RequestParam String status) {
        if (!status.equals("Y") || !status.equals("N")) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        Map<String, Object> update = new HashMap<>();
        update.put("memberNo", memberNo);
        update.put("memberStatus", status);

        int result = adminRepository.updateMemberStatus(update);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 회원 권한수정
     *
     * @param memberNo
     * @param role
     */
    @PostMapping("/member/{memberNo}/role")
    public void changeMemberRole(@PathVariable Long memberNo, @RequestParam String role) {
        if (!role.equals("Y") || !role.equals("N")) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        Map<String, Object> update = new HashMap<>();
        update.put("memberNo", memberNo);
        update.put("admin", role);

        int result = adminRepository.updateMemberRole(update);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 전체 회원수
     *
     * @return int
     */
    @GetMapping("/member-count")
    public Integer countMember() {
        return adminRepository.countMemberList();
    }

    @GetMapping("/enroll-count")
    public Map<String, List> countEnrollMember() {
        List countMemberList = adminRepository.countEnrollMember30Days();
        List enrollDateList = adminRepository.enrollDate30Days();
        Map<String, List> countMemberAnd30Days = new HashMap<>();
        countMemberAnd30Days.put("enrollDate", enrollDateList);
        countMemberAnd30Days.put("count", countMemberList);
        return countMemberAnd30Days;
    }

    @GetMapping("/product")
    public Map<String, Object> adminProductView(@RequestParam(value = "page", required = false) Integer currentPage,
                                                @RequestParam(required = false) String product) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (product == null) {
            product = "ALL";
        }
        return adminService.pageAndProductList(currentPage, product);
    }

    @GetMapping("/shop/total")
    public Map<String, Integer> totalSales() {
        return adminService.totalSaleList();
    }

    // TODO 통계
}
