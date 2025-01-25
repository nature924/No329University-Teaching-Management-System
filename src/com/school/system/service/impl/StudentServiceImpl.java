package com.school.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.school.system.dao.CourseStudentMapper;
import com.school.system.dao.MajorCourseMapper;
import com.school.system.dao.StudentMapper;
import com.school.system.domain.CourseStudentExample;
import com.school.system.domain.MajorCourseExample;
import com.school.system.domain.Student;
import com.school.system.domain.StudentExample;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.MajorCourseDto;
import com.school.system.domain.dto.StudentDto;
import com.school.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private MajorCourseMapper majorCourseMapper;
    @Autowired
    private CourseStudentMapper courseStudentMapper;

    @Override
    public Student checkLogin(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStudentNumEqualTo(student.getStudentNum()).andStudentPasswordEqualTo(student.getStudentPassword());
        List<Student> students = studentMapper.selectByExample(studentExample);
        return students != null && students.size() > 0 ? students.get(0) : null;
    }

    @Override
    public Student getStudentBySNum(String studentNum) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andStudentNumEqualTo(studentNum);
        List<Student> students = studentMapper.selectByExample(studentExample);
        return students != null && students.size() > 0 ? students.get(0) : null;
    }

    @Override
    public int updateStudent(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andIdEqualTo(student.getId());
        return studentMapper.updateByExampleSelective(student, studentExample);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectByExample(new StudentExample());
    }

    @Override
    public Student getStudentById(String id) {
        return studentMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public int insertStudent(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public int deleteById(int id) {
        return studentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<StudentDto> getAllStudentDtos() {
        return studentMapper.selectAllStudentDtos();
    }

    @Override
    public JSONArray getStudentCourses(Integer studentId) {
        Student student = studentMapper.selectByPrimaryKey(studentId);
        Integer majorId = student.getStudentMajorId();
        MajorCourseExample majorCourseExample = new MajorCourseExample();
        MajorCourseExample.Criteria criteria = majorCourseExample.createCriteria();
        criteria.andMcMajorIdEqualTo(majorId);
        String[] monday = new String[10];
        String[] tuesday = new String[10];
        String[] wednesday = new String[10];
        String[] thursday = new String[10];
        String[] friday = new String[10];
        String[] saturday = new String[10];
        String[] sunday = new String[10];
        // 根据专业找到所有必修课
        List<MajorCourseDto> majorCoursesDtos = majorCourseMapper.selectMajorCourseDtosByMajor(majorId);
        // 找到学生选择的所有选修课
        List<CourseDto> optionCourseDtos = courseStudentMapper.selectCourseDtosByStudentId(studentId);
        for (MajorCourseDto majorCourseDto : majorCoursesDtos)
            switch (majorCourseDto.getCourseWeekday()) {
                case 1:
                    setCourses(monday, majorCourseDto);
                    break;
                case 2:
                    setCourses(tuesday, majorCourseDto);
                    break;
                case 3:
                    setCourses(wednesday, majorCourseDto);
                    break;
                case 4:
                    setCourses(thursday, majorCourseDto);
                    break;
                case 5:
                    setCourses(friday, majorCourseDto);
                    break;
                case 6:
                    setCourses(saturday, majorCourseDto);
                    break;
                case 7:
                    setCourses(sunday, majorCourseDto);
                    break;
            }
        for (CourseDto optionCourseDto : optionCourseDtos)
            switch (optionCourseDto.getCourseWeekday()) {
                case 1:
                    setCourses(monday, optionCourseDto);
                    break;
                case 2:
                    setCourses(tuesday, optionCourseDto);
                    break;
                case 3:
                    setCourses(wednesday, optionCourseDto);
                    break;
                case 4:
                    setCourses(thursday, optionCourseDto);
                    break;
                case 5:
                    setCourses(friday, optionCourseDto);
                    break;
                case 6:
                    setCourses(saturday, optionCourseDto);
                    break;
                case 7:
                    setCourses(sunday, optionCourseDto);
                    break;
            }
        JSONArray courses = new JSONArray();
        courses.add(monday);
        courses.add(tuesday);
        courses.add(wednesday);
        courses.add(thursday);
        courses.add(friday);
        courses.add(saturday);
        courses.add(sunday);
        return courses;
    }

    @Override
    public int removeStudentFromCourseById(int studentId, int courseId) {
        CourseStudentExample courseStudentExample = new CourseStudentExample();
        CourseStudentExample.Criteria criteria = courseStudentExample.createCriteria();
        criteria.andCsStudentIdEqualTo(studentId).andCsCourseIdEqualTo(courseId);
        return courseStudentMapper.deleteByExample(courseStudentExample);
    }

    @Override
    public boolean checkExist(String studentNum) {
        StudentExample example = new StudentExample();
        StudentExample.Criteria criteria = example.createCriteria();
        criteria.andStudentNumEqualTo(studentNum);
        return studentMapper.selectByExample(example).size() > 0;
    }

    private void setCourses(String[] weekday, CourseDto optionCourseDto) {
        setCoursesName(weekday, optionCourseDto.getCourseTime(), optionCourseDto.getId() + "#" + optionCourseDto.getCourseName() + "@" + optionCourseDto.getCoursePlace());
    }

    private void setCourses(String[] weekday, MajorCourseDto majorCourseDto) {
        setCoursesName(weekday, majorCourseDto.getCourseTime(), majorCourseDto.getId() + "#" + majorCourseDto.getCourseName() + "@" + majorCourseDto.getCoursePlace());
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
}
