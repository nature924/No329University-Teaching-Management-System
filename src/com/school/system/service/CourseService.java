package com.school.system.service;

import com.school.system.domain.Course;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.StudentFileDto;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    List<Course> getAllOptionalCourses();

    Course getCouseById(String id);

    int updateCourse(Course course);

    int insertCourse(Course course);

    int deleteById(int id);

    List<CourseDto> getAllOptionalCourseDtos();

    int insertCourseStudent(String courseId, Integer studentId);

    int deleteCourseStudent(String courseId, Integer studentId);

    List<CourseDto> getAllCourseDtos();

    CourseDto getCouseDtoById(Integer id);

    List<StudentFileDto> getCourseStudentsByCourseId(int courseId);
}
