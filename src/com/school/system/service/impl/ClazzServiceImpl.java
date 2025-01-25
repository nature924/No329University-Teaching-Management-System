package com.school.system.service.impl;

import com.school.system.dao.ClazzMapper;
import com.school.system.domain.Clazz;
import com.school.system.domain.ClazzExample;
import com.school.system.domain.dto.ClazzDto;
import com.school.system.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public List<Clazz> getAllClazzs() {
        return clazzMapper.selectByExample(new ClazzExample());
    }

    @Override
    public Clazz getClazzById(String id) {
        return clazzMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public int updateClazz(Clazz clazz) {
        ClazzExample clazzExample = new ClazzExample();
        ClazzExample.Criteria criteria = clazzExample.createCriteria();
        criteria.andIdEqualTo(clazz.getId());
        return clazzMapper.updateByExampleSelective(clazz, clazzExample);
    }

    @Override
    public int insertClazz(Clazz clazz) {
        return clazzMapper.insert(clazz);
    }

    @Override
    public int deleteById(int id) {
        return clazzMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ClazzDto> getAllClazzDtos() {
        return clazzMapper.selectAllClazzDtos();
    }

	@Override
	public boolean checkExist(String clazzName) {
		ClazzExample example = new ClazzExample();
		ClazzExample.Criteria criteria = example.createCriteria();
		criteria.andClazzNameEqualTo(clazzName);
		return clazzMapper.selectByExample(example).size()>0;
	}
}
