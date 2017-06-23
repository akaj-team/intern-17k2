package vn.asiantech.internship.day15.model;

import java.io.Serializable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class Question implements Serializable{
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String result;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String result) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.result = result;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
