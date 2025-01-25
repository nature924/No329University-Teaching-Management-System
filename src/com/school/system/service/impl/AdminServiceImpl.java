package com.school.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.school.system.dao.AdminMapper;
import com.school.system.domain.Admin;
import com.school.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin checkLogin(Admin admin) {
        Admin adminDB = adminMapper.selectAdminByAdminNum(admin.getAdminNum());
        return adminDB != null && StringUtils.equals(adminDB.getAdminPassword(), admin.getAdminPassword()) ? adminDB : null;
    }

    @Override
    public Admin getAdminByAdminNum(String adminNum) {
        return adminMapper.selectAdminByAdminNum(adminNum);
    }

    @Override
    public int updateAdmin(Admin admin) {
        return adminMapper.updateAdmin(admin);
    }
}
