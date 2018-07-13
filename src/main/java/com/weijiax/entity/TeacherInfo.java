package com.weijiax.entity;

public class TeacherInfo {

    private String teacher_name;

    private String teacher_number;

    private String university;

    public TeacherInfo(String teacher_name,String teacher_number,String university){
        this.teacher_name = teacher_name;
        this.teacher_number = teacher_number;
        this.university = university;
    }


    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_number() {
        return teacher_number;
    }

    public void setTeacher_number(String teacher_number) {
        this.teacher_number = teacher_number;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
