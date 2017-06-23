package vn.asiantech.internship.day15.model;

import java.io.Serializable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class Answer implements Serializable{
    private String answer;

    public Answer() {
    }

    public Answer(String answer) {

        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
