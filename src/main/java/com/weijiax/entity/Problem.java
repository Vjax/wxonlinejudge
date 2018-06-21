package com.weijiax.entity;

public class Problem {

    private int problem_id;

    private String title;

    private String subject_type;

    private byte pass_rate;

    private byte difficulty;

    private String description;

    public Problem(){

    }

    public Problem(int problem_id,String title,String subject_type,byte pass_rate,byte difficulty,String description){
        this.pass_rate = pass_rate;
        this.problem_id = problem_id;
        this.title = title;
        this.subject_type = subject_type;
        this.description = description;
        this.difficulty = difficulty;
    }


    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getPass_rate() {
        return pass_rate;
    }

    public void setPass_rate(byte pass_rate) {
        this.pass_rate = pass_rate;
    }

    public byte getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(byte difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(String subject_type) {
        this.subject_type = subject_type;
    }
}
