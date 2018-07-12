package com.weijiax.entity;

public class University {

    private String university_name;

    private int university_id;

    public University(String university_name,int university_id){
        this.university_id = university_id;
        this.university_name = university_name;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(int university_id) {
        this.university_id = university_id;
    }
}
