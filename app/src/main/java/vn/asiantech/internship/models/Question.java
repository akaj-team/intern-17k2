package vn.asiantech.internship.models;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Models Question Created by Thanh Thien on 6/23/17.
 */
public class Question implements Serializable {
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String answerCorrect;

    public Question() {
    }

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String answerCorrect) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerCorrect = answerCorrect;
    }

    protected Question(Parcel in) {
        question = in.readString();
        answerA = in.readString();
        answerB = in.readString();
        answerC = in.readString();
        answerD = in.readString();
        answerCorrect = in.readString();
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

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

}
