package vn.asiantech.internship.ui.view;

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

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.adapter.ViewPagerAdapter;
import vn.asiantech.internship.ui.dialog.SubmitDialog;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTvPrevious;
    private TextView mTvNext;
    private TextView mTvResult;
    private TextView mTvTitle;
    private ViewPager mViewPagerQuestion;

    private ArrayList<Question> mQuestionList;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
        initViewPager();
        setOnClick();
    }

    private void initView(){
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mTvPrevious = (TextView) findViewById(R.id.tvPrevious);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mViewPagerQuestion = (ViewPager) findViewById(R.id.viewPagerQuestion);
    }

    private void setOnClick(){
        mTvPrevious.setOnClickListener(this);
        mTvResult.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
        mTvNext.setVisibility(View.VISIBLE);
    }

    private void initViewPager(){
        mQuestionList = Question.getQuestion(loadJsonFromAssets(), 10);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mQuestionList);
        mViewPagerQuestion.setAdapter(mPagerAdapter);

        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0){
                    mTvPrevious.setVisibility(View.VISIBLE);
                } else {
                    mTvPrevious.setVisibility(View.GONE);
                }
                if (position < mQuestionList.size() - 1){
                    mTvNext.setVisibility(View.VISIBLE);
                    mTvResult.setVisibility(View.GONE);
                } else {
                    mTvNext.setVisibility(View.GONE);
                    mTvResult.setVisibility(View.VISIBLE);
                }
                mTvTitle.setText(String.format(getString(R.string.text_view_test_activity_file_name), position + 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public JSONArray loadJsonFromAssets(){
        JSONArray jsonArray = null;
        String json = null;
        try {
            InputStream inputStream = getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buff = new byte[size];
            inputStream.read(buff);
            inputStream.close();
            json = new String(buff, "UTF-8");
            jsonArray = new JSONArray(json);

        } catch (IOException e){
            Log.i("loadJsonFromAssets: ", e.getMessage());
            return null;
        } catch (JSONException e){
            Log.i("loadJsonFromAssets: ", e.getMessage());
        }
        return jsonArray;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvPrevious:
                mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() - 1);
                break;
            case R.id.tvNext:
                mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() + 1);
                break;
            case R.id.tvResult:
                showDialogSubmit();
                break;
        }
    }

    void showDialogSubmit(){
        DialogFragment dialogFragment = SubmitDialog.getInstance(mQuestionList);
        dialogFragment.show(getSupportFragmentManager(), "");
    }
}
