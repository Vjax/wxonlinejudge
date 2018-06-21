package com.weijiax.entity;

import java.sql.Time;

public class StudentHomework {

    private int grade;

    private String title;

    private String teacher_name;

    private String finished_time;

    private int pass_rate;

    private int problem_id;

    public StudentHomework(int problem_id,int grade,String title,String teacher_name,String finished_time,int pass_rate){
        this.problem_id = problem_id;
        this.grade = grade;
        this.title = title;
        this.teacher_name = teacher_name;
        this.finished_time = finished_time;
        this.pass_rate = pass_rate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public int getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(int pass_rate) {
        this.pass_rate = pass_rate;
    }

    public String getFinished_time() {
        return finished_time;
    }

    public void setFinished_time(String finished_time) {
        this.finished_time = finished_time;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }
}
