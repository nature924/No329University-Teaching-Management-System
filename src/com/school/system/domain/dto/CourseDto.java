package com.school.system.domain.dto;

import com.school.system.domain.Course;

public class CourseDto extends Course {
    private String courseTeacherName;

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public void setCourseTeacherName(String courseTeacherName) {
        this.courseTeacherName = courseTeacherName;
    }
}
