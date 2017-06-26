package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Hai on 6/25/2017.
 */

public class Result implements Parcelable {
    private int questionId;
    private boolean isCorrect;

    public Result() {
    }

    public Result(int questionId, boolean isCorrect) {
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    protected Result(Parcel in) {
        questionId = in.readInt();
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionId);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
    }
}
