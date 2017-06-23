package vn.asiantech.internship.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class TestActivity extends AppCompatActivity{
    private TextView mTvPrevious;
    private TextView mTvNext;
    private ViewPager mViewPagerQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
    }

    private void initView(){
        mTvNext = (TextView) findViewById(R.id.tvNext);
        mTvPrevious = (TextView) findViewById(R.id.tvPrevious);
        mViewPagerQuestion = (ViewPager) findViewById(R.id.viewPagerQuestion);
    }
}
