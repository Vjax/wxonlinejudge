package com.weijiax.entity;

public class StudentInfo {

    private String student_name;

    private String student_number;

    public StudentInfo(String student_name,String student_number){
        this.student_name = student_name;
        this.student_number = student_number;
    }


    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }
}
