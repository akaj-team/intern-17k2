package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ducle on 23/06/2017.
 */
public class Question implements Parcelable {
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String answerRight;
    private boolean isChecked;
    private boolean isCheckedRight;

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String answerRight) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerRight = answerRight;
        isChecked = false;
        isCheckedRight = false;
    }

    protected Question(Parcel in) {
        question = in.readString();
        answerA = in.readString();
        answerB = in.readString();
        answerC = in.readString();
        answerD = in.readString();
        answerRight = in.readString();
        isChecked = in.readByte() != 0;
        isCheckedRight = in.readByte() != 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getAnswerRight() {
        return answerRight;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCheckedRight() {
        return isCheckedRight;
    }

    public void setCheckedRight(boolean checkedRight) {
        isCheckedRight = checkedRight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answerA);
        dest.writeString(answerB);
        dest.writeString(answerC);
        dest.writeString(answerD);
        dest.writeString(answerRight);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isCheckedRight ? 1 : 0));
    }
}
