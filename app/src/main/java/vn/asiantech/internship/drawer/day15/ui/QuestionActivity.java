package vn.asiantech.internship.drawer.day15.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.adapter.QuestionPagerAdapter;
import vn.asiantech.internship.drawer.day15.models.Question;

public class QuestionActivity extends AppCompatActivity {

    private static final String END_OF_NEXT_BUTTON = "Result";
    private static final String NEXT_BUTTON = "Next";

    private Button mBtnPrev;
    private Button mBtnNext;
    private TextView mTvQuestionTitle;
    private ViewPager mViewPagerQuestion;
    private QuestionPagerAdapter mPagerAdapter;
    private List<Question> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initUI();
        createQuestion();
        addEvents();
    }

    private void addEvents() {
        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // TODO: 25/06/2017
                // set title for this question
                updateButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() - 1, true);
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (TextUtils.equals(mTvNext.getText().toString(), "Result")) {
                    showDialog();
                } else {
                    mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() + 1, true);
                }*/
                if (END_OF_NEXT_BUTTON.equals(mBtnNext.getText().toString())){
                    Toast.makeText(QuestionActivity.this, "Show result", Toast.LENGTH_SHORT).show();
                }else {
                    mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() + 1, true);
                }
            }
        });
    }

    /*private void showDialog() {
        android.app.FragmentManager fm = this.getFragmentManager();
        ResultDialog resultDialog = ResultDialog.newInstance(mTests);
        resultDialog.show(fm, null);
    }*/

    private void updateButton(int position) {
        if (position == 0) {
            mBtnPrev.setVisibility(View.INVISIBLE);
        } else {
            mBtnPrev.setVisibility(View.VISIBLE);
        }
        if (position == mPagerAdapter.getCount() - 1) {
            mBtnNext.setText(END_OF_NEXT_BUTTON);
        } else {
            mBtnNext.setText(NEXT_BUTTON);
        }
    }

    private void createQuestion() {
        mQuestions = new JSONHandler(getApplicationContext()).getQuestions();
        Collections.shuffle(mQuestions); // xao tron danh sach
        mPagerAdapter = new QuestionPagerAdapter(getSupportFragmentManager(), mQuestions);
        mViewPagerQuestion.setAdapter(mPagerAdapter);
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarTest);
        setSupportActionBar(toolbar);
        mBtnPrev = (Button) findViewById(R.id.btnPrev);
        mBtnNext = (Button) findViewById(R.id.btnNext);
        mTvQuestionTitle = (TextView) findViewById(R.id.tvQuestionTitle);
        mViewPagerQuestion = (ViewPager) findViewById(R.id.viewPagerQuestion);
    }
}
