package com.school.system.dao;

import com.school.system.domain.Admin;

public interface AdminMapper {
    Admin selectAdminByAdminNum(String adminNum);

    int updateAdmin(Admin admin);
}
