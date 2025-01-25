package com.school.system.service.impl;

import com.school.system.dao.EvaluateMapper;
import com.school.system.domain.Evaluate;
import com.school.system.domain.EvaluateExample;
import com.school.system.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluateServiceImpl implements EvaluateService {

    @Autowired
    private EvaluateMapper evaluateMapper;

    @Override
    public int createEvaluate(Evaluate evaluate) {
        EvaluateExample evaluateExample = new EvaluateExample();
        EvaluateExample.Criteria criteria = evaluateExample.createCriteria();
        criteria.andEvaluateTeacheridEqualTo(evaluate.getEvaluateTeacherid()).andEvaluateStudentidEqualTo(evaluate.getEvaluateStudentid());
        List<Evaluate> evaluates = evaluateMapper.selectByExample(evaluateExample);
        if (evaluates != null && evaluates.size() > 0) {
            return 0;
        }
        return evaluateMapper.insert(evaluate);
    }
}
