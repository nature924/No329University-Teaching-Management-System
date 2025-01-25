package com.school.system.service;

import com.school.system.domain.Admin;

public interface AdminService {
    Admin checkLogin(Admin admin);

    Admin getAdminByAdminNum(String adminNum);

    int updateAdmin(Admin admin);
}
