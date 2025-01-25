package com.school.system.domain.dto;

public class MajorCourseDto {
    private Integer id;
    private String courseName;
    private Integer courseMajorId;
    private String courseMajorName;
    private Integer courseTeacherId;
    private String courseTeacherName;
    private Integer courseWeekday;
    private Integer courseTime;
    private String coursePlace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseMajorId() {
        return courseMajorId;
    }

    public void setCourseMajorId(Integer courseMajorId) {
        this.courseMajorId = courseMajorId;
    }

    public String getCouseMajorName() {
        return courseMajorName;
    }

    public void setCouseMajorName(String courseMajorName) {
        this.courseMajorName = courseMajorName;
    }

    public Integer getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseTeacherId(Integer courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public void setCourseTeacherName(String courseTeacherName) {
        this.courseTeacherName = courseTeacherName;
    }

    public Integer getCourseWeekday() {
        return courseWeekday;
    }

    public void setCourseWeekday(Integer courseWeekday) {
        this.courseWeekday = courseWeekday;
    }

    public Integer getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    public String getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }
}
