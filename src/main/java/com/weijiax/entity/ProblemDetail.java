package com.weijiax.entity;

public class ProblemDetail {

    private String title;

    private String code;

    private String sample_input;

    private String sample_output;

    private String description;

    private String hint;

    public ProblemDetail(String title,String code,String sample_input,String sample_output,String description,String hint){
        this.code = code;
        this.sample_input = sample_input;
        this.sample_output = sample_output;
        this.description = description;
        this.hint = hint;
        this.title = title;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSample_input() {
        return sample_input;
    }

    public void setSample_input(String sample_input) {
        this.sample_input = sample_input;
    }

    public String getSample_output() {
        return sample_output;
    }

    public void setSample_output(String sample_output) {
        this.sample_output = sample_output;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
