package com.weijiax.entity;

import java.util.List;

public class Problems {

    private List<Problem> problems;

    public Problems(List<Problem> problems){
        this.problems = problems;
    }


    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
}
