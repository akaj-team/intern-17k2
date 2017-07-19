package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class MainTestFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private ViewPager mViewPagerQuestion;
    private QuestionAdapter mQuestionAdapter;
    private List<Question> mQuestions;
    private TextView mTvTitle;
    private TextView mTvLeft;
    private TextView mTvRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_main, container, false);
        mQuestions = new ArrayList<>();
        mQuestions = initData();
        initViews(view);
        setViewPager();
        mTvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
        return view;
    }

    private void setViewPager() {
        mQuestionAdapter = new QuestionAdapter(getFragmentManager(), mQuestions);
        mViewPagerQuestion.setAdapter(mQuestionAdapter);
        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvTitle.setText(getString(R.string.question, position + 1));
                if (position == 0) {
                    mTvLeft.setVisibility(View.GONE);
                } else {
                    mTvLeft.setVisibility(View.VISIBLE);
                }
                if (position == 9) {
                    mTvRight.setText(getString(R.string.result));
                } else {
                    mTvRight.setText(getString(R.string.next));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews(View view) {
        mViewPagerQuestion = (ViewPager) view.findViewById(R.id.viewPagerQuestion);
        mTvTitle = (TextView) view.findViewById(R.id.tvTitleQuestion);
        mTvLeft = (TextView) view.findViewById(R.id.tvLeft);
        mTvRight = (TextView) view.findViewById(R.id.tvRight);
    }

    private List<Question> initData() {
        List<Question> questions = new ArrayList<>();
        List<Question> allQuestions = getListQuestion();
        Vector vector = new Vector();
        Random random = new Random();
        int value;
        int i = 0;
        while (i < 10) {
            value = random.nextInt(allQuestions.size());
            if (!vector.contains(value)) {
                vector.add(value);
                i++;
            }
        }
        for (int j = 0; j < vector.size(); j++) {
            questions.add(allQuestions.get((int) vector.get(j)));
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
            Log.d("tag11", "ERROR");
        }
        return questions;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLeft:
                mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() - 1);
                break;
            case R.id.tvRight:
                if (mViewPagerQuestion.getCurrentItem() != 9) {
                    mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() + 1);
                } else {

                    showConfirmResultDialog();
                }
                break;
        }
    }

    private void showConfirmResultDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        ResultDialogFragment resultDialogFragment = ResultDialogFragment.newInstance((ArrayList<Question>) mQuestions);
        resultDialogFragment.show(fragmentManager, "Dialog confirm show result");
    }
}
