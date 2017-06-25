package vn.asiantech.internship.exday15;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by datbu on 23-06-2017.
 */
public class ItemQuestion implements Parcelable {
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String answerTrue;
    private int answer;
    private List<ItemQuestion> mItem;
    public ItemQuestion(String question, String answerA, String answerB, String answerC, String answerD, String answerTrue) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerTrue = answerTrue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ItemQuestion(JSONObject jsonObject) {
        try {
            this.question = jsonObject.getString("question");
            this.answerTrue = jsonObject.getString("answer_right");
            this.answerA=jsonObject.getString("answer_a");
            this.answerB=jsonObject.getString("answer_b");
            this.answerC=jsonObject.getString("answer_c");
            this.answerD=jsonObject.getString("answer_d");
            Random random = new Random();
            mItem = new ArrayList<>();
            mItem.add(new ItemQuestion(question,answerA,answerB,answerC,answerD,answerTrue));
            List<String> answer = new ArrayList<>();
            while (mItem.size() > 0) {
                int rand = random.nextInt(mItem.size());
                answer.add(String.valueOf(mItem.get(rand)));
                mItem.remove(rand);
                Log.d("tag","mitem"+mItem);
            }
        } catch (JSONException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeString(this.answerA);
        dest.writeString(this.answerB);
        dest.writeString(this.answerC);
        dest.writeString(this.answerD);
        dest.writeString(this.answerTrue);
        dest.writeInt(this.answer);
    }

    private ItemQuestion(Parcel in) {
        this.question = in.readString();
        this.answerA = in.readString();
        this.answerB = in.readString();
        this.answerC = in.readString();
        this.answerD = in.readString();
        this.answerTrue = in.readString();
        this.answer = in.readInt();
    }

    public static final Parcelable.Creator<ItemQuestion> CREATOR = new Parcelable.Creator<ItemQuestion>() {
        @Override
        public ItemQuestion createFromParcel(Parcel source) {
            return new ItemQuestion(source);
        }

        @Override
        public ItemQuestion[] newArray(int size) {
            return new ItemQuestion[size];
        }
    };

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

    public String getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(String answerTrue) {
        this.answerTrue = answerTrue;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
