package com.school.system.service;

import com.school.system.domain.Score;
import com.school.system.domain.dto.ScoreDto;

import java.util.List;

public interface ScoreService {
    Score getScoreByStudentIdAndCourseId(Integer studentId, Integer courseId);

    int createScore(Score score);

    int updateScore(Score score);

    List<ScoreDto> getScoreDtosByStudentId(Integer studentId);
}
