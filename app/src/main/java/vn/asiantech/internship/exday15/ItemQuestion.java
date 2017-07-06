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
class ItemQuestion implements Parcelable {
    private String question;
    private String answerTrue;
    private List<String> answers;
    private int answer = -1;

    @Override
    public int describeContents() {
        return 0;
    }

    ItemQuestion(JSONObject jsonObject) {
        try {
            this.question = jsonObject.getString("question");
            this.answerTrue = jsonObject.getString("answer_right");
            List<String> answerList = new ArrayList<>();
            answerList.add(jsonObject.getString("answer_a"));
            answerList.add(jsonObject.getString("answer_b"));
            answerList.add(jsonObject.getString("answer_c"));
            answerList.add(jsonObject.getString("answer_d"));
            answers = new ArrayList<>();
            Random random = new Random();
            while (answerList.size() > 0) {
                int rand = random.nextInt(answerList.size());
                answers.add(String.valueOf(answerList.get(rand)));
                answerList.remove(rand);
            }
        } catch (JSONException e) {
            Log.d("tag11", e.getMessage());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeStringList(this.answers);
        dest.writeString(this.answerTrue);
        dest.writeInt(this.answer);
    }

    private ItemQuestion(Parcel in) {
        this.question = in.readString();
        this.answers = in.createStringArrayList();
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

    boolean isRight() {
        if (answer > 4 || answer < 0) {
            return false;
        } else if (answerTrue.equals(answers.get(answer))) {
            return true;
        }
        return false;
    }

    List<String> getAnswers() {
        return answers;
    }

    String getQuestion() {
        return question;
    }

    int getAnswer() {
        return answer;
    }

    void setAnswer(int answer) {
        this.answer = answer;
    }
}
