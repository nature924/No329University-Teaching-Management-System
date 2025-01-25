package com.school.system.domain.dto;

public class TeacherDto {
    private Integer id;
    private String teacherName;
    private String teacherNum;
    private Integer teacherMajorId;
    private String teacherMajorName;
    private String teacherPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(String teacherNum) {
        this.teacherNum = teacherNum;
    }

    public Integer getTeacherMajorId() {
        return teacherMajorId;
    }

    public void setTeacherMajorId(Integer teacherMajorId) {
        this.teacherMajorId = teacherMajorId;
    }

    public String getTeacherMajorName() {
        return teacherMajorName;
    }

    public void setTeacherMajorName(String teacherMajorName) {
        this.teacherMajorName = teacherMajorName;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }
}
