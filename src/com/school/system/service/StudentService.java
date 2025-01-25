package com.school.system.service;

import com.alibaba.fastjson.JSONArray;
import com.school.system.domain.Student;
import com.school.system.domain.dto.StudentDto;

import java.util.List;

public interface StudentService {
    Student checkLogin(Student student);

    Student getStudentBySNum(String studentNum);

    int updateStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(String id);

    int insertStudent(Student student);

    int deleteById(int parseInt);

    List<StudentDto> getAllStudentDtos();

    JSONArray getStudentCourses(Integer studentId);

    int removeStudentFromCourseById(int studentId, int courseId);

    boolean checkExist(String studentNum);
}
