package com.school.system.domain.dto;

import com.school.system.domain.Student;

public class StudentDto extends Student {
    private String studentClassName;
    private String studentMajorName;

    public String getStudentClassName() {
        return studentClassName;
    }

    public void setStudentClassName(String studentClassName) {
        this.studentClassName = studentClassName;
    }

    public String getStudentMajorName() {
        return studentMajorName;
    }

    public void setStudentMajorName(String studentMajorName) {
        this.studentMajorName = studentMajorName;
    }
}
