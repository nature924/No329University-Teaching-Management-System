package com.school.system.service;

import com.school.system.domain.Paper;

public interface PaperService {
    Paper getPaperByStudentIdAndCourseId(Integer studentId, Integer courseId);

    int createPaper(Paper paper);
}
