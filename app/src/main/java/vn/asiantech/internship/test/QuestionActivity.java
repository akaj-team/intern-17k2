package vn.asiantech.internship.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

/**
 * Used to display  viewPager that contain question fragments.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
public class QuestionActivity extends AppCompatActivity {
    private QuestionViewPagerAdapter mAdapter;
    private TextView mTvPrev;
    private TextView mTvNext;
    private TextView mTvBigQuestion;
    private ViewPager mQuestionViewPager;
    private List<Test> mTests;
    private static final String TAG = "error";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTvBigQuestion = (TextView) findViewById(R.id.tvBigQuestion);
        mTvPrev = (TextView) findViewById(R.id.tvPrev);
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mQuestionViewPager = (ViewPager) findViewById(R.id.testViewPager);
        mTests = new ArrayList<>();
        mTests.addAll(getObject());
        Collections.shuffle(mTests);
        mAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager(), mTests);
        mQuestionViewPager.setAdapter(mAdapter);

        mQuestionViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateButton(position);
                mTvBigQuestion.setText(getString(R.string.textView_question) + " " + (position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionViewPager.setCurrentItem(mQuestionViewPager.getCurrentItem() - 1, true);
            }
        });

        mTvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(mTvNext.getText().toString(), "Result")) {
                    showDialog();
                } else {
                    mQuestionViewPager.setCurrentItem(mQuestionViewPager.getCurrentItem() + 1, true);

                }
            }
        });
    }

    private void updateButton(int position) {
        mTvPrev.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        mTvNext.setText(position == mAdapter.getCount() - 1 ? "Result" : "Next");
    }

    private void showDialog() {
        android.app.FragmentManager fm = this.getFragmentManager();
        ResultDialog resultDialog = ResultDialog.newInstance(mTests, new QuestionFragment().getCorrect());
        resultDialog.show(fm, null);
    }

    private String loadJSONFromAsset() {
        String json;
        try {
            InputStream inputStream = getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
        return json;
    }

    private List<Test> getObject() {
        List<Test> questions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = jsonObject.getJSONArray("test");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jb = (JSONObject) jsonArray.get(i);
                questions.add(new Test(jb.getString("question"), jb.getString("answer_a"), jb.getString("answer_b"),
                        jb.getString("answer_c"), jb.getString("answer_d"), jb.getString("answer_right")));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return questions;
    }
}
