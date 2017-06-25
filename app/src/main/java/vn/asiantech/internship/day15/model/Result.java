package vn.asiantech.internship.day15.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 25/06/2017.
 */
public class Result implements Parcelable {
    private String question;
    private boolean result;

    public Result(String question, boolean result) {
        this.question = question;
        this.result = result;
    }

    public Result() {
    }

    public Result(Parcel in) {
        question = in.readString();
        result = in.readByte() != 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeByte((byte) (result ? 1 : 0));
    }
}
