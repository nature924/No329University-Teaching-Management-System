package com.school.system.service;

import com.school.system.domain.Clazz;
import com.school.system.domain.dto.ClazzDto;

import java.util.List;

public interface ClazzService {
    List<Clazz> getAllClazzs();

    Clazz getClazzById(String id);

    int updateClazz(Clazz clazz);

    int insertClazz(Clazz clazz);

    int deleteById(int id);

    List<ClazzDto> getAllClazzDtos();
    
    boolean checkExist(String clazzName);
}
