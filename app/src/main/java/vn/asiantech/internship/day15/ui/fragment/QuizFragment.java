package vn.asiantech.internship.day15.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.adapter.QuizAdapter;
import vn.asiantech.internship.day15.model.Question;
import vn.asiantech.internship.day15.model.Result;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class QuizFragment extends Fragment {
    private static final String TYPE_QUESTION_ARRAY = "Question";
    private static final String TYPE_QUESTION = "question";
    private static final String TYPE_ANSWER_A = "answer_a";
    private static final String TYPE_ANSWER_B = "answer_b";
    private static final String TYPE_ANSWER_C = "answer_c";
    private static final String TYPE_ANSWER_D = "answer_d";
    private static final String TYPE_ANSWER_RIGHT = "answer_right";


    private ImageView mImgPrevious;
    private ImageView mImgNext;
    private TextView mTvQuiz;
    private TextView mTvResult;
    private List<Question> mQuestions;
    private List<Question> mQuestionRandoms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestions = getData();
        mQuestionRandoms = getListQuestionRandom();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        final ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewPagerQuestion);
        mImgPrevious = (ImageView) v.findViewById(R.id.imgPrevious);
        mImgNext = (ImageView) v.findViewById(R.id.imgNext);
        mTvQuiz = (TextView) v.findViewById(R.id.tvQuiz);
        mTvResult = (TextView) v.findViewById(R.id.tvResultLabel);
        QuizAdapter quizAdapter = new QuizAdapter(getFragmentManager(), mQuestionRandoms);
        viewPager.setAdapter(quizAdapter);
        mTvQuiz.setText(R.string.textview_quiz_text);
        mImgPrevious.setVisibility(View.INVISIBLE);
        mImgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
        mImgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvQuiz.setText(getString(R.string.question, position + 1));
                if (position == 0) {
                    mImgPrevious.setVisibility(View.INVISIBLE);
                } else {
                    mImgPrevious.setVisibility(View.VISIBLE);
                }
                if (position == 9) {
                    mImgNext.setVisibility(View.INVISIBLE);
                    mTvResult.setVisibility(View.VISIBLE);
                } else {
                    mImgNext.setVisibility(View.VISIBLE);
                    mTvResult.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        quizAdapter.notifyDataSetChanged();
        mTvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Result> results = new ArrayList<>();
                int j = 0;
                for (int i = 0; i < mQuestionRandoms.size(); i++) {
                    if (!mQuestionRandoms.get(i).getAnswered().equals("")) {
                        j++;
                    }
                    boolean check = mQuestionRandoms.get(i).getAnswered().equals(mQuestionRandoms.get(i).getResult());
                    results.add(new Result("Question " + (i + 1), check));
                }
                MyDialogFragment.init(results, "" + j + "/10").show(getFragmentManager(), "dialog");
            }
        });
        return v;
    }


    private List<Question> getData() {
        String jsonText = null;
        List<Question> questions = new ArrayList<>();
        try {
            jsonText = readText(getActivity(), R.raw.question);
        } catch (IOException e) {
            Log.v("Exception", "IOException when get JSON from resource");
        }
        try {
            JSONObject jsonRoot = new JSONObject(jsonText);
            JSONArray jsonArray = jsonRoot.getJSONArray(TYPE_QUESTION_ARRAY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                questions.add(new Question(jsonObject.getString(TYPE_QUESTION), jsonObject.getString(TYPE_ANSWER_A), jsonObject.getString(TYPE_ANSWER_B), jsonObject.getString(TYPE_ANSWER_C), jsonObject.getString(TYPE_ANSWER_D), "", jsonObject.getString(TYPE_ANSWER_RIGHT)));
            }
        } catch (JSONException e) {
            Log.v("Exception", "JSONException");
        }
        return questions;
    }

    // Read content text from json resource
    private String readText(Context context, int resId) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(resId)));
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    // Create random Question
    private List<Question> getListQuestionRandom() {
        ArrayList<Question> questionRandom = new ArrayList<>();
        Vector<Integer> v = new Vector<>();
        Random random = new Random();
        for (int i = 0; i < mQuestions.size(); i++) {
            int position = random.nextInt(mQuestions.size());
            while (v.contains(position)) {
                position = random.nextInt(mQuestions.size());
            }
            v.add(position);
            Vector<Integer> v1 = new Vector<>();
            List<String> answers = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                int vt = random.nextInt(4);
                while (v1.contains(vt)) {
                    vt = random.nextInt(4);
                }
                switch (vt) {
                    case 0:
                        answers.add(mQuestions.get(position).getAnswerA());
                        break;
                    case 1:
                        answers.add(mQuestions.get(position).getAnswerB());
                        break;
                    case 2:
                        answers.add(mQuestions.get(position).getAnswerC());
                        break;
                    default:
                        answers.add(mQuestions.get(position).getAnswerD());
                }
                v1.add(vt);
            }
            questionRandom.add(new Question(mQuestions.get(position).getQuestionQuiz(), answers.get(0), answers.get(1), answers.get(2), answers.get(3), "", mQuestions.get(position).getResult()));
        }
        return questionRandom;
    }
}
