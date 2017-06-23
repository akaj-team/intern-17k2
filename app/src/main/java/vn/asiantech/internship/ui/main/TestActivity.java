package vn.asiantech.internship.ui.main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.TestAdapter;
import vn.asiantech.internship.dialog.ConfirmDialog;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.fragments.QuestionFragment;

/**
 * Created by PC on 6/23/2017.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvNext;
    private TextView mTvPrev;
    private TextView mTvResult;
    private TextView mTvTitle;

    private ViewPager mViewPagerContent;
    private List<QuestionFragment> mQuestionFragments;
    private List<Question> mQuestions;
    private TestAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mViewPagerContent = (ViewPager) findViewById(R.id.viewPagerContent);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mTvPrev = (TextView) findViewById(R.id.tvPrev);
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mTvResult = (TextView) findViewById(R.id.tvResult);

        mTvPrev.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvResult.setOnClickListener(this);


        mQuestions = Question.GetQuestionSet(loadJSONFromAsset(), 10);
        mQuestionFragments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            QuestionFragment questionFragment = QuestionFragment.getNewInstance(mQuestions.get(i), i);
            questionFragment.setListener(new QuestionFragment.OnDataChanged() {
                @Override
                public void onAnswerChoosed(int question, int answer) {
                    mQuestions.get(question).setUserAnswer(answer);
                    Log.i("tag11", question + "--" + answer);
                }
            });
            mQuestionFragments.add(questionFragment);
        }
        mAdapter = new TestAdapter(getSupportFragmentManager(), mQuestionFragments);
        mViewPagerContent.setAdapter(mAdapter);

        mViewPagerContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    mTvPrev.setVisibility(View.VISIBLE);
                } else {
                    mTvPrev.setVisibility(View.GONE);
                }
                if (position < mQuestions.size() - 1) {
                    mTvNext.setVisibility(View.VISIBLE);
                    mTvResult.setVisibility(View.GONE);
                } else {
                    mTvNext.setVisibility(View.GONE);
                    mTvResult.setVisibility(View.VISIBLE);
                }
                mTvTitle.setText(String.format(getString(R.string.title_format), position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public JSONArray loadJSONFromAsset() {
        JSONArray jsonArray = null;
        String json = null;
        try {
            InputStream is = getAssets().open("question.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private int getCountQuestionComplete() {
        int count = 0;
        for (Question q : mQuestions) {
            if (q.getUserAnswer() > -1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPrev:
                mViewPagerContent.setCurrentItem(mViewPagerContent.getCurrentItem() - 1);
                break;
            case R.id.tvNext:
                mViewPagerContent.setCurrentItem(mViewPagerContent.getCurrentItem() + 1);
                break;
            case R.id.tvResult:
                Log.i("tag11", getCountQuestionComplete() + "");
                break;
        }
    }

    void showDialog(int count, int complete) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = ConfirmDialog.getNewInstance(count, complete);
        //newFragment.show(ft, "dialog");
    }
}