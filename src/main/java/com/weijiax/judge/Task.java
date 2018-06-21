package com.weijiax.judge;

/**
 * 执行的任务
 */
public class Task {

    private int solution_id;
    private int problem_id;
    private int user_id;
    private String language;
    private String role;
    private int homeword_id;
    private boolean is_test;
    private boolean isFinished;

    public Task(int problem_id, int user_id, String language,String role,int homework_id,boolean is_test){
        this.problem_id = problem_id;
        this.user_id = user_id;
        this.language = language;
        this.role = role;
        this.homeword_id = homework_id;
        this.is_test = is_test;
    }

    public int getSolution_id() {
        return solution_id;
    }

    public void setSolution_id(int solution_id) {
        this.solution_id = solution_id;
    }

    public int getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(int problem_id) {
        this.problem_id = problem_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void taskDone(){
        isFinished = true;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getHomeword_id() {
        return homeword_id;
    }

    public void setHomeword_id(int homeword_id) {
        this.homeword_id = homeword_id;
    }

    public boolean isIs_test() {
        return is_test;
    }

    public void setIs_test(boolean is_test) {
        this.is_test = is_test;
    }
}
