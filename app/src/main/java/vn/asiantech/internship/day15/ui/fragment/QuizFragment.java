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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.adapter.QuizAdapter;
import vn.asiantech.internship.day15.model.Answer;
import vn.asiantech.internship.day15.model.Question;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */

public class QuizFragment extends Fragment {
    private ViewPager mViewPager;
    private ImageView mImgPrevious;
    private ImageView mImgNext;
    private TextView mTvQuiz;
    private TextView mTvResult;
    private QuizAdapter mQuizAdapter;
    private List<Question> mQuestions;
    private List<Question> mQuestionsRandom;
    private List<Answer> mAnswers=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPagerQuestion);
        mImgPrevious = (ImageView) v.findViewById(R.id.imgPrevious);
        mImgNext = (ImageView) v.findViewById(R.id.imgNext);
        mTvQuiz = (TextView) v.findViewById(R.id.tvQuiz);
        mTvResult= (TextView) v.findViewById(R.id.tvResultLabel);
        mQuestions=getData();
        getAnswer();
        mQuizAdapter=new QuizAdapter(getFragmentManager(),mQuestionsRandom,mAnswers);
        mViewPager.setAdapter(mQuizAdapter);
        mTvQuiz.setText("Question 1");
        mImgPrevious.setVisibility(View.INVISIBLE);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvQuiz.setText("Question "+""+(position+1));
                if(position==0){
                    mImgPrevious.setVisibility(View.INVISIBLE);
                }
                else{
                    mImgPrevious.setVisibility(View.VISIBLE);
                }
                if(position==9){
                    mImgNext.setVisibility(View.INVISIBLE);
                    mTvResult.setVisibility(View.VISIBLE);
                }
                else{
                    mImgNext.setVisibility(View.VISIBLE);
                    mTvResult.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mQuizAdapter.notifyDataSetChanged();
        mTvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: create fragment dialog
            }
        });
        return v;
    }

    private void getAnswer() {
        for(int i=0;i<mQuestions.size();i++){
            mAnswers.add(new Answer(" "));
        }
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
            JSONArray jsonArray = jsonRoot.getJSONArray("Question");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                questions.add(new Question(jsonObject.getString("question"),jsonObject.getString("answer_a"),jsonObject.getString("answer_b"),jsonObject.getString("answer_c"),jsonObject.getString("answer_d"),jsonObject.getString("answer_right")));
            }
        } catch (JSONException e) {
            Log.v("Exception", "JSONException");
        }
       return questions;
    }

    // Read content text from json resource
    private String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
