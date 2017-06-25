package vn.asiantech.internship.ui.questions;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * QuestionMainFragment of Question
 * Created by Thanh Thiem
 */
public class QuestionMainFragment extends Fragment implements View.OnClickListener{

    private TextView mTvBack;
    private TextView mTvHead;
    private TextView mTvNext;
    private ViewPager mViewPager;

    private List<Question> mQuestions;
    private List<Question> mQuestionChoice;
    private List<Integer> mHasAdd;
    private String[][] mAnswerChoice = new String[0][2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_main, container, false);
        initView(v);

        mHasAdd = new ArrayList<>();
        mQuestions = getJson();
        mQuestionChoice = getQuestionResult(new ArrayList<Question>(), 0);
        QuestionAdapter questionAdapter = new QuestionAdapter(getActivity().getSupportFragmentManager(), mQuestionChoice);
        mViewPager.setAdapter(questionAdapter);

        mTvNext.setOnClickListener(this);
        mTvBack.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setHeader(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return v;
    }

    private void initView(View v) {
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mTvBack = (TextView) v.findViewById(R.id.tvBack);
        mTvNext = (TextView) v.findViewById(R.id.tvNext);
        mTvHead = (TextView) v.findViewById(R.id.tvHead);
        setHeader(1);
        if (getActivity() instanceof QuestionActivity) {
            ((QuestionActivity) getActivity()).setToolbar(toolbar);
        }
    }

    // Random 10 question and change position of answers
    private List<Question> getQuestionResult(List<Question> currentQuestions, int position) {
        if (currentQuestions.size() == 10) {
            return currentQuestions;
        } else {
            // Get random question
            int i = getPosition();

            // Get random answers
            List<Integer> answers = new ArrayList<>();
            int posA = getPosAnswer(answers);
            String answer_a = getAnswer(i, posA);
            answers.add(posA);
            int posB = getPosAnswer(answers);
            String answer_b = getAnswer(i, posB);
            answers.add(posB);
            int posC = getPosAnswer(answers);
            String answer_c = getAnswer(i, posC);
            answers.add(posC);
            int posD = getPosAnswer(answers);
            String answer_d = getAnswer(i, posD);
            Question question = new Question(mQuestions.get(i).getQuestion(), answer_a, answer_b, answer_c, answer_d, mQuestions.get(i).getAnswerCorrect());
            currentQuestions.add(question);
            position++;
            return getQuestionResult(currentQuestions, position);
        }
    }

    private String getAnswer(int position, int i) {
        if (i == 0) {
            return mQuestions.get(position).getAnswerA();
        } else if (i == 1) {
            return mQuestions.get(position).getAnswerB();
        } else if (i == 2) {
            return mQuestions.get(position).getAnswerC();
        } else {
            return mQuestions.get(position).getAnswerD();
        }
    }

    private int getPosAnswer(List<Integer> answers) {
        int i = new Random().nextInt(4);
        String s = answers.toString();
        if (s.contains(i + "")) {
            return getPosAnswer(answers);
        }
        return i;
    }

    private int getPosition() {
        int i = new Random().nextInt(mQuestions.size());
        for (int a = 0; a < mHasAdd.size(); a++) {
            if (i == mHasAdd.get(a)) {
                return getPosition();
            }
        }
        mHasAdd.add(i);
        return i;
    }

    public void setHeader(int position) {
        String head = getResources().getString(R.string.question) + " " + position;
        mTvHead.setText(head);
        if (position == 1) {
            mTvBack.setText(null);
            mTvNext.setText(R.string.next);
        } else if (position == 10) {
            mTvBack.setText(R.string.prev);
            mTvNext.setText(R.string.result);
        } else {
            mTvBack.setText(R.string.prev);
            mTvNext.setText(R.string.next);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBack:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.tvNext:
                if (mTvNext.getText().toString().equalsIgnoreCase(getResources().getString(R.string.result))) {
                    showResult();
                } else {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                break;
        }
    }

    private void showDialogFragment(int i) {
        // Create and show the dialog.
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        DialogFragment dialogFragment = QuestionDialogFragment.newInstance(i);
        dialogFragment.show(ft, "dialog");
    }

    private void replaceData(int i, String answer) {
        mAnswerChoice[i][1] = answer;
    }

    private void showResult() {
        if (mAnswerChoice.length < 10) {
            showDialogFragment(mAnswerChoice.length);
        } else {
            showResultFragment();
        }
    }

    public void showResultFragment() {
        boolean[] booleans = new boolean[10];
        int rightCorrect = 0;
        for (int i = 0; i < 10; i++) {
            if (isCorrect(i)) {
                rightCorrect++;
                booleans[i] = true;
            }
        }
        if (getActivity() instanceof QuestionActivity) {
            ((QuestionActivity) getActivity()).replaceFragment(QuestionResultFragment.newInstance(booleans, rightCorrect), false);
        }
    }

    private boolean isCorrect(int i) {
        for (String[] aMAnswerChoice : mAnswerChoice) {
            if (Integer.parseInt(aMAnswerChoice[0]) == i && aMAnswerChoice[1].equalsIgnoreCase(mQuestionChoice.get(i).getAnswerCorrect())) {
                return true;
            }
        }
        return false;
    }

    public String[][] addOneIntToArray(int question, String answer) {

        String[][] newArray = new String[mAnswerChoice.length + 1][2];
        for (int index = 0; index < mAnswerChoice.length; index++) {
            newArray[index][0] = mAnswerChoice[index][0];
            newArray[index][1] = mAnswerChoice[index][1];
        }

        newArray[newArray.length - 1][0] = question + "";
        newArray[newArray.length - 1][1] = answer;
        return newArray;
    }

    private List<Question> getJson() {
        List<Question> questions = new ArrayList<>();
        try {
            String json = loadJSONFromAsset();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                String questionString = object.getString("question");
                String answerA = object.getString("answer_a");
                String answerB = object.getString("answer_b");
                String answerC = object.getString("answer_c");
                String answerD = object.getString("answer_d");
                String answerCorrect = object.getString("answer_right");
                Question question = new Question(questionString, answerA, answerB, answerC, answerD, answerCorrect);
                questions.add(question);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void onClickAnswer(int question, String answer) {
        for (int i = 0; i < mAnswerChoice.length; i++) {
            if (question == Integer.parseInt(mAnswerChoice[i][0])) {
                replaceData(i, answer);
                return;
            }
        }
        mAnswerChoice = addOneIntToArray(question, answer);
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getActivity().getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
