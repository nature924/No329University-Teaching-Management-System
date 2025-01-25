package com.school.system.domain.dto;

public class ClazzDto {
    private Integer id;
    private String clazzName;
    private String clazzMajorId;
    private String clazzMajorName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getClazzMajorId() {
        return clazzMajorId;
    }

    public void setClazzMajorId(String clazzMajorId) {
        this.clazzMajorId = clazzMajorId;
    }

    public String getClazzMajorName() {
        return clazzMajorName;
    }

    public void setClazzMajorName(String clazzMajorName) {
        this.clazzMajorName = clazzMajorName;
    }

    @Override
    public String toString() {
        return "ClazzDto{" +
                "id=" + id +
                ", clazzName='" + clazzName + '\'' +
                ", clazzMajorId='" + clazzMajorId + '\'' +
                ", clazzMajorName='" + clazzMajorName + '\'' +
                '}';
    }
}
