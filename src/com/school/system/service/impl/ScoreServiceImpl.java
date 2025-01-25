package com.school.system.service.impl;

import com.school.system.dao.ScoreMapper;
import com.school.system.domain.Score;
import com.school.system.domain.ScoreExample;
import com.school.system.domain.dto.ScoreDto;
import com.school.system.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public Score getScoreByStudentIdAndCourseId(Integer studentId, Integer courseId) {
        ScoreExample scoreExample = new ScoreExample();
        ScoreExample.Criteria criteria = scoreExample.createCriteria();
        criteria.andScoreStudentIdEqualTo(studentId).andScoreCourseIdEqualTo(courseId);
        List<Score> scores = scoreMapper.selectByExample(scoreExample);
        return scores != null && scores.size() > 0 ? scores.get(0) : null;
    }

    @Override
    public int createScore(Score score) {
        return scoreMapper.insert(score);
    }

    @Override
    public int updateScore(Score score) {
        return scoreMapper.updateByPrimaryKey(score);
    }

    @Override
    public List<ScoreDto> getScoreDtosByStudentId(Integer studentId) {
        return scoreMapper.selectScoreDtosByStudentId(studentId);
    }
}
