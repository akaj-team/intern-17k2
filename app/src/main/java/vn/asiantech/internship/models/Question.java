package vn.asiantech.internship.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class Question implements Serializable {
    private String question;
    private List<String> answers;
    private int userAnswer = -1;
    private String rightAnswer;

    public Question(JSONObject q) {
        try {
            this.question = q.getString("question");
            this.rightAnswer = q.getString("answer_right");
            List<String> answerList = new ArrayList<>();
            answerList.add(q.getString("answer_a"));
            answerList.add(q.getString("answer_b"));
            answerList.add(q.getString("answer_c"));
            answerList.add(q.getString("answer_d"));
            Random random = new Random();
            answers = new ArrayList<>();
            while (answerList.size() > 0) {
                int rand = random.nextInt(answerList.size());
                answers.add(answerList.get(rand));
                answerList.remove(rand);
            }
        } catch (JSONException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getUserAnswer() {
        return userAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isRight() {
        if (userAnswer > 4 || userAnswer < 0) {
            return false;
        }
        if (rightAnswer.equals(answers.get(userAnswer))) {
            return true;
        }
        return false;
    }

    public static List<Question> GetQuestionSet(JSONArray jsonArray, int dataSetLenght) {
        List<Question> questionSet = new ArrayList<>();
        List<JSONObject> jsonObjects = new ArrayList<>();
        Random random = new Random();
        int index;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                jsonObjects.add(jsonArray.getJSONObject(i));
            } catch (JSONException e) {
                Log.i("tag11", e.getMessage());
                return null;
            }
        }

        for (int i = 0; i < dataSetLenght; i++) {
            index = random.nextInt(jsonObjects.size());
            questionSet.add(new Question(jsonObjects.get(index)));
            jsonObjects.remove(index);
        }
        return questionSet;
    }

}
