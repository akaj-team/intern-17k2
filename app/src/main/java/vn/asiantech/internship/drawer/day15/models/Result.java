package vn.asiantech.internship.drawer.day15.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by at-dinhvo on 26/06/2017.
 */

public class Result implements Parcelable {

    private String question;
    private boolean isCorrect;

    public Result(String question, boolean isCorrect) {
        this.question = question;
        this.isCorrect = isCorrect;
    }

    protected Result(Parcel in) {
        question = in.readString();
        isCorrect = in.readByte() != 0;
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

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeByte((byte) (isCorrect ? 1 : 0));
    }
}
