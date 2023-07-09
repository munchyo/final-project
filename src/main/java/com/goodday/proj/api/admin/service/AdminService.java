package com.goodday.proj.api.admin.service;

import java.util.Map;

public interface AdminService {
    Map<String, Object> allHelpView(Integer currentPage, String reply, String type);

    Map<String, Object> pagingAndMemberList(Integer currentPage, String searchId, String searchNickname);

    Map<String, Object> pageAndProductList(Integer currentPage, String product);

    Map<String, Integer> totalSaleList();

}
