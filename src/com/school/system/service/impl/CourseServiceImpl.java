package com.school.system.service.impl;

import com.school.system.dao.CourseMapper;
import com.school.system.dao.CourseStudentMapper;
import com.school.system.dao.MajorCourseMapper;
import com.school.system.dao.StudentMapper;
import com.school.system.domain.*;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.StudentFileDto;
import com.school.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseStudentMapper courseStudentMapper;
    @Autowired
    private MajorCourseMapper majorCourseMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Course> getAllCourses() {
        CourseExample courseExample = new CourseExample();
        return courseMapper.selectByExample(courseExample);
    }

    @Override
    public List<Course> getAllOptionalCourses() {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCourseTypeEqualTo(2);
        return courseMapper.selectByExample(courseExample);
    }

    @Override
    public Course getCouseById(String id) {
        return courseMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public int updateCourse(Course course) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andIdEqualTo(course.getId());
        return courseMapper.updateByExampleSelective(course, courseExample);
    }

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insert(course);
    }

    @Override
    public int deleteById(int id) {
        return courseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<CourseDto> getAllOptionalCourseDtos() {
        return courseMapper.selectAllOptionalCourseDtos();
    }

    @Override
    public int insertCourseStudent(String courseId, Integer studentId) {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCsCourseId(Integer.parseInt(courseId));
        courseStudent.setCsStudentId(studentId);
        CourseStudentExample courseStudentExample = new CourseStudentExample();
        CourseStudentExample.Criteria criteria = courseStudentExample.createCriteria();
        criteria.andCsCourseIdEqualTo(Integer.parseInt(courseId)).andCsStudentIdEqualTo(studentId);
        List<CourseStudent> courseStudents = courseStudentMapper.selectByExample(courseStudentExample);
        if (courseStudents == null || courseStudents.size() <= 0) {
            return courseStudentMapper.insert(courseStudent);
        }
        return 0;
    }

    @Override
    public int deleteCourseStudent(String courseId, Integer studentId) {
        CourseStudentExample courseStudentExample = new CourseStudentExample();
        CourseStudentExample.Criteria criteria = courseStudentExample.createCriteria();
        criteria.andCsCourseIdEqualTo(Integer.parseInt(courseId)).andCsStudentIdEqualTo(studentId);
        return courseStudentMapper.deleteByExample(courseStudentExample);
    }

    @Override
    public List<CourseDto> getAllCourseDtos() {
        return courseMapper.selectAllCourseDtos();
    }

    @Override
    public CourseDto getCouseDtoById(Integer id) {
        return courseMapper.selectCourseDtoById(id);
    }

    @Override
    public List<StudentFileDto> getCourseStudentsByCourseId(int courseId) {
        Course course = courseMapper.selectByPrimaryKey(courseId);
        // 必修课
        if (course.getCourseType() == 1) {
            return studentMapper.selectStudentDtosByMajorId(courseId);
        } else {    // 选修
            return courseStudentMapper.selectStudentDtosByCourseId(courseId);
        }
    }
}
