package com.goodday.proj.api.admin.service;

import java.util.Map;

public interface AdminService {
    Map<String, Object> allHelpView(Integer currentPage, String reply, String type);
}
