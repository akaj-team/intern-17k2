package vn.asiantech.internship.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Question Model.
 * Created by huypham on 23-Jun-17.
 */
public final class Question implements Serializable, Parcelable {
    private String quest;
    private List<String> answers;
    private String correctAnswer;
    private int answerChoose = -1;

    public String getQuestion() {
        return quest;
    }

    public void setQuestion(String question) {
        this.quest = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getAnswerChoose() {
        return answerChoose;
    }

    public void setAnswerChoose(int answerChoose) {
        this.answerChoose = answerChoose;
    }

    private Question(JSONObject object) {
        try {
            this.quest = object.getString("question");
            this.correctAnswer = object.getString("answer_right");
            List<String> answerList = new ArrayList<>();
            answerList.add(object.getString("answer_a"));
            answerList.add(object.getString("answer_b"));
            answerList.add(object.getString("answer_c"));
            answerList.add(object.getString("answer_d"));

            Random random = new Random();
            answers = new ArrayList<>();
            while (answerList.size() > 0) {
                int rand = random.nextInt(answerList.size());
                answers.add(answerList.get(rand));
                answerList.remove(rand);
            }
        } catch (JSONException e) {
            Log.i("Question", e.getMessage());
        }
    }

    private Question(Parcel in) {
        quest = in.readString();
        answers = in.createStringArrayList();
        answerChoose = in.readInt();
        correctAnswer = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quest);
        dest.writeStringList(answers);
        dest.writeInt(answerChoose);
        dest.writeString(correctAnswer);
    }

    public boolean isCorrect() {
        if (answerChoose > 4 || answerChoose < 0) {
            return false;
        } else if (correctAnswer.equals(answers.get(answerChoose))) {
            return true;
        }
        return false;
    }

    public static ArrayList<Question> getQuestion(JSONArray jsonArray, int dataSetLength) {
        ArrayList<Question> questionList = new ArrayList<>();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        Random random = new Random();
        int index;

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObjectList.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                Log.i("Question ", e.getMessage());
                return null;
            }
        }

        for (int i = 0; i < dataSetLength; i++) {
            index = random.nextInt(jsonObjectList.size());
            questionList.add(new Question(jsonObjectList.get(index)));
            jsonObjectList.remove(index);
        }
        return questionList;
    }
}
