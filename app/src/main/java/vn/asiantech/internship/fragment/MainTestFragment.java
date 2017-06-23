package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.QuestionAdapter;
import vn.asiantech.internship.models.Question;

/**
 * Created by ducle on 23/06/2017.
 */

public class MainTestFragment extends android.support.v4.app.Fragment {
    private ViewPager mViewPagerQuestion;
    private QuestionAdapter mQuestionAdapter;
    private List<Question> mQuestions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_main, container, false);
        mQuestions = new ArrayList<>();
        mQuestions = getListQuestion();
        initData();

        mViewPagerQuestion = (ViewPager) view.findViewById(R.id.viewPagerQuestion);
        mQuestionAdapter = new QuestionAdapter(getFragmentManager(), mQuestions);
        mViewPagerQuestion.setAdapter(mQuestionAdapter);
        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private List<Question> initData() {
        List<Question> questions = new ArrayList<>();
        List<Question> allQuestions = getListQuestion();
        Vector vector = new Vector();
        Random random = new Random();
        int value;
        for (int i = 0; i < 10; ) {
            value = random.nextInt(20);
            if (!vector.contains(value)) {
                vector.add(value);
                i++;
            }
        }
        for (int j = 0; j < vector.size(); j++) {
            questions.add(allQuestions.get((Integer) vector.get(j)));
        }
        return questions;
    }

    private List<Question> getListQuestion() {
        String json = null;
        List<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = getActivity().getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String question = jsonObject.getString("question");
                String answerA = jsonObject.getString("answer_a");
                String answerB = jsonObject.getString("answer_b");
                String answerC = jsonObject.getString("answer_c");
                String answerD = jsonObject.getString("answer_d");
                String answerRight = jsonObject.getString("answer_right");
                questions.add(new Question(question, answerA, answerB, answerC, answerD, answerRight));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }
}
