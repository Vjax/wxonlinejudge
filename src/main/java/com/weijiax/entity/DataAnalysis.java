package com.weijiax.entity;

public class DataAnalysis {

    private int remain;

    private int accepted;

    private int fail;

    public DataAnalysis(int remain,int accepted,int fail){
        this.remain = remain;
        this.accepted = accepted;
        this.fail = fail;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
