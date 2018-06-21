package com.weijiax.entity;

public class Teacher {

    private int teacher_id;
    private String name;
    private String university;
    private String language;

    public Teacher(int teacher_id,String name,String university,String language){
        this.teacher_id = teacher_id;
        this.language = language;
        this.name = name;
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
