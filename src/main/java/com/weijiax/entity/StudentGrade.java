package com.weijiax.entity;

import java.util.List;

public class StudentGrade {

    private String name;

    private List<Integer> grades;

    public StudentGrade(String name,List<Integer> grades){
        this.name = name;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }
}
