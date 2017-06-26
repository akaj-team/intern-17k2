package vn.asiantech.internship.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Test;
import vn.asiantech.internship.models.Result;

/**
 * Activity contain ViewPager
 * <p>
 * Created by Hai on 6/25/2017.
 */

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, QuestionFragment.OnListener {
    private static final String JSON_FILE_NAME = "question.json";
    public static final String KEY_SEND_LIST = "data";
    public static final int QUESTION_SIZE = 10;

    private TextView mTvQuestionTitle;
    private TextView mTvPrevious;
    private TextView mTvNext;

    private ViewPager mViewPagerQuestion;
    private int mPosition = 0;
    private List<Result> mResults = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        mViewPagerQuestion = (ViewPager) findViewById(R.id.viewPagerQuestion);
        for (int i = 0; i < QUESTION_SIZE; i++) {
            mResults.add(new Result(i, false));
        }
        initToolbar();
        initAdapter();
    }

    private void initAdapter() {
        List<Test> tests = getQuestion();
        Collections.shuffle(tests);
        QuestionAdapter questionAdapter = new QuestionAdapter(getSupportFragmentManager(), tests);
        mViewPagerQuestion.setAdapter(questionAdapter);
        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                updateToolbarQuestion(position);
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initToolbar() {
        mTvQuestionTitle = (TextView) findViewById(R.id.tvQuestionTitle);
        mTvPrevious = (TextView) findViewById(R.id.tvPrevius);
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mTvPrevious.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
    }

    private void updateToolbarQuestion(int position) {
        mTvQuestionTitle.setText(getString(R.string.question_text, position + 1));
        mTvPrevious.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        mTvNext.setText(position == QUESTION_SIZE - 1 ? getResources().getString(R.string.question_result_text) :
                getResources().getString(R.string.question_tvnext_text));
    }

    private List<Test> getQuestion() {
        List<Test> tests = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(loadJsonFromAssets());
            JSONArray jsonArray = obj.getJSONArray("question");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObj = (JSONObject) jsonArray.get(i);
                String question = questionObj.getString("question");
                String answerA = questionObj.getString("answer_a");
                String answerB = questionObj.getString("answer_b");
                String answerC = questionObj.getString("answer_c");
                String answerD = questionObj.getString("answer_d");
                String correctAnswer = questionObj.getString("answer_right");
                tests.add(new Test(question, answerA, answerB, answerC, answerD, correctAnswer));
            }
        } catch (JSONException e) {
            e.getMessage();
        }
        return tests;
    }

    private String loadJsonFromAssets() {
        String json;
        try {
            InputStream is = getAssets().open(JSON_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.getMessage();
            return null;
        }
        return json;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPrevius:
                mViewPagerQuestion.setCurrentItem(mPosition - 1);
                break;
            case R.id.tvNext:
                if (mPosition == QUESTION_SIZE - 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    QuestionDialogFragment dialog = new QuestionDialogFragment().newInstance(mResults);
                    dialog.show(fm, null);
                }
                mViewPagerQuestion.setCurrentItem(mPosition + 1);
                break;
        }
    }

    @Override
    public void onSendData(Result result) {
        if (mResults.contains(result)) {
            mResults.remove(result);
        } else {
            mResults.remove(mPosition);
        }
        mResults.add(mPosition, result);
    }
}
