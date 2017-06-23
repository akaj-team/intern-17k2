package vn.asiantech.internship.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * As a question object.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */

public class Question implements Parcelable {
    private String question;
    private String answer_a;
    private String answer_b;
    private String answer_c;
    private String answer_d;
    private String answer_right;
    private boolean isTrue;

    public Question(String question, String answer_a, String answer_b, String answer_c, String answer_d, String answer_right) {
        this.question = question;
        this.answer_a = answer_a;
        this.answer_b = answer_b;
        this.answer_c = answer_c;
        this.answer_d = answer_d;
        this.answer_right = answer_right;
    }

    private Question(Parcel in) {
        question = in.readString();
        answer_a = in.readString();
        answer_b = in.readString();
        answer_c = in.readString();
        answer_d = in.readString();
        answer_right = in.readString();
        isTrue = in.readByte() != 0;
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

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer_a() {
        return answer_a;
    }

    public void setAnswer_a(String answer_a) {
        this.answer_a = answer_a;
    }

    public String getAnswer_b() {
        return answer_b;
    }

    public void setAnswer_b(String answer_b) {
        this.answer_b = answer_b;
    }

    public String getAnswer_c() {
        return answer_c;
    }

    public void setAnswer_c(String answer_c) {
        this.answer_c = answer_c;
    }

    public String getAnswer_d() {
        return answer_d;
    }

    public void setAnswer_d(String answer_d) {
        this.answer_d = answer_d;
    }

    public String getAnswer_right() {
        return answer_right;
    }

    public void setAnswer_right(String answer_right) {
        this.answer_right = answer_right;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(question);
        parcel.writeString(answer_a);
        parcel.writeString(answer_b);
        parcel.writeString(answer_c);
        parcel.writeString(answer_d);
        parcel.writeString(answer_right);
        parcel.writeByte((byte) (isTrue ? 1 : 0));
    }
}
