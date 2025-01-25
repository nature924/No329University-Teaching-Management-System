package com.school.system.service.impl;

import com.school.system.dao.PaperMapper;
import com.school.system.domain.Paper;
import com.school.system.domain.PaperExample;
import com.school.system.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public Paper getPaperByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        PaperExample paperExample = new PaperExample();
        PaperExample.Criteria criteria = paperExample.createCriteria();
        criteria.andPaperCourseIdEqualTo(courseId).andPaperStudentIdEqualTo(studentId);
        List<Paper> papers = paperMapper.selectByExample(paperExample);
        return papers != null && papers.size() > 0 ? papers.get(0) : null;
    }

    @Override
    public int createPaper(Paper paper) {
        return paperMapper.insert(paper);
    }
}
