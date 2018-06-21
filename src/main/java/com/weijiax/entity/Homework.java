package com.weijiax.entity;

import java.sql.Time;
import java.util.Date;

public class Homework {

    private int homework_id;

    private Problem problem;

    private String teacher_name;

    private String start_time;

    private String end_time;

    public Homework(Problem problem,String teacher_name,String end_time,String start_time,int homework_id)
    {
        this.teacher_name = teacher_name;
        this.end_time = end_time;
        this.problem = problem;
        this.start_time = start_time;
        this.homework_id = homework_id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }


    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(int homework_id) {
        this.homework_id = homework_id;
    }
}
