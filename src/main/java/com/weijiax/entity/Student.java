package com.weijiax.entity;

public class Student {
    private int student_id;

    private String student_name;

    private String student_number;

    public Student(String student_name,String student_number,int student_id){
        this.student_name = student_name;
        this.student_number = student_number;
        this.student_id = student_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
