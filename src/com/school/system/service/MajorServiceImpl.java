package com.school.system.service;

import com.school.system.dao.MajorMapper;
import com.school.system.domain.Major;
import com.school.system.domain.MajorExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<Major> getAllMajors() {
        return majorMapper.selectByExample(new MajorExample());
    }
}
