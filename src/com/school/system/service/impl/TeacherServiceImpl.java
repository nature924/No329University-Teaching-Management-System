package com.school.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.school.system.dao.CourseMapper;
import com.school.system.dao.TeacherMapper;
import com.school.system.domain.Course;
import com.school.system.domain.CourseExample;
import com.school.system.domain.Teacher;
import com.school.system.domain.TeacherExample;
import com.school.system.domain.dto.TeacherDto;
import com.school.system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Teacher checkLogin(Teacher teacher) {
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andTeacherNumEqualTo(teacher.getTeacherNum()).andTeacherPasswordEqualTo(teacher.getTeacherPassword());
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return teachers != null && teachers.size() > 0 ? teachers.get(0) : null;
    }

    @Override
    public Teacher getTeacherByTeacherNum(String teacherNum) {
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andTeacherNumEqualTo(teacherNum);
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        return teachers != null && teachers.size() > 0 ? teachers.get(0) : null;
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        TeacherExample teacherExample = new TeacherExample();
        TeacherExample.Criteria criteria = teacherExample.createCriteria();
        criteria.andIdEqualTo(teacher.getId());
        return teacherMapper.updateByExampleSelective(teacher, teacherExample);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.selectByExample(new TeacherExample());
    }

    @Override
    public List<TeacherDto> getAllTeacherDtos() {
        return teacherMapper.selectAllTeacherDtos();
    }

    @Override
    public JSONArray getTeacherCourses(Integer teacherId) {
        // 找到courseTeacherId为该教师的所有课程
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andCourseTeacheridEqualTo(teacherId);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        String[] monday = new String[10];
        String[] tuesday = new String[10];
        String[] wednesday = new String[10];
        String[] thursday = new String[10];
        String[] friday = new String[10];
        String[] saturday = new String[10];
        String[] sunday = new String[10];
        for (Course course : courses)
            switch (course.getCourseWeekday()) {
                case 1:
                    setCourses(monday, course);
                    break;
                case 2:
                    setCourses(tuesday, course);
                    break;
                case 3:
                    setCourses(wednesday, course);
                    break;
                case 4:
                    setCourses(thursday, course);
                    break;
                case 5:
                    setCourses(friday, course);
                    break;
                case 6:
                    setCourses(saturday, course);
                    break;
                case 7:
                    setCourses(sunday, course);
                    break;
            }
        JSONArray coursesJ = new JSONArray();
        coursesJ.add(monday);
        coursesJ.add(tuesday);
        coursesJ.add(wednesday);
        coursesJ.add(thursday);
        coursesJ.add(friday);
        coursesJ.add(saturday);
        coursesJ.add(sunday);
        return coursesJ;
    }

    @Override
    public boolean checkExist(String teacherNum) {
        TeacherExample example = new TeacherExample();
        TeacherExample.Criteria criteria = example.createCriteria();
        criteria.andTeacherNumEqualTo(teacherNum);
        return teacherMapper.selectByExample(example).size() > 0;
    }

    private void setCourses(String[] weekday, Course Course) {
        setCoursesName(weekday, Course.getCourseTime(), Course.getId() + "#" + Course.getCourseName() + "@" + Course.getCoursePlace());
    }

    private void setCoursesName(String[] weekday, Integer courseTime, String courseName) {
        switch (courseTime) {
            case 1:
                weekday[0] = courseName;
                weekday[1] = courseName;
                break;
            case 2:
                weekday[2] = courseName;
                weekday[3] = courseName;
                break;
            case 3:
                weekday[4] = courseName;
                weekday[5] = courseName;
                break;
            case 4:
                weekday[6] = courseName;
                weekday[7] = courseName;
                break;
            case 5:
                weekday[8] = courseName;
                weekday[9] = courseName;
                break;
        }
    }

    @Override
    public Teacher getTeacherById(String id) {
        return teacherMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public int insertTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    @Override
    public int deleteById(int id) {
        return teacherMapper.deleteByPrimaryKey(id);
    }
}
