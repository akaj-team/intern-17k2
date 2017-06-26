package vn.asiantech.internship.drawer.day15.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.adapter.QuestionPagerAdapter;
import vn.asiantech.internship.drawer.day15.models.Question;
import vn.asiantech.internship.drawer.day15.models.Result;

public class QuestionActivity extends AppCompatActivity {

    public static final String RESULT_KEY = "result_key";
    private static final String END_OF_NEXT_BUTTON = "Result";
    private static final String NEXT_BUTTON = "Next";
    private static final String EXIT_BUTTON = "Exit";
    private static final String RESET_BUTTON = "Reset";
    private static final String TITLE = "Question";

    private Button mBtnPrev;
    private Button mBtnNext;
    private TextView mTvQuestionTitle;
    private ViewPager mViewPagerQuestion;
    private FrameLayout mFrameLayout;

    private QuestionPagerAdapter mPagerAdapter;
    private List<Question> mQuestions;
    private ArrayList<Result> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initUI();
        createQuestion();
        addEvents();
    }

    private void addEvents() {
        mResults = new ArrayList<>();
        mViewPagerQuestion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvQuestionTitle.setText(TITLE + " " + (position + 1));
                updateButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RESET_BUTTON.equals(mBtnPrev.getText().toString())) {
                    mFrameLayout.setVisibility(View.GONE);
                    mViewPagerQuestion.setVisibility(View.VISIBLE);
                    mViewPagerQuestion.setCurrentItem(0);
                } else {
                    mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() - 1, true);
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (END_OF_NEXT_BUTTON.equals(mBtnNext.getText().toString())) {
                    mViewPagerQuestion.setVisibility(View.GONE);
                    mBtnNext.setText(EXIT_BUTTON);
                    mBtnPrev.setText(RESET_BUTTON);
//                    showDialog();
                } else if (EXIT_BUTTON.equals(mBtnNext.getText().toString())) {
                    finish();
                } else {
                    Question currentQuestion = mQuestions.get(mViewPagerQuestion.getCurrentItem());
                    mResults.add(new Result(currentQuestion.getQuestion(), currentQuestion.isCorrect()));
                    mViewPagerQuestion.setCurrentItem(mViewPagerQuestion.getCurrentItem() + 1, true);
                }
            }
        });
    }

    /*private void showResult() {
        mTvQuestionTitle.setText(R.string.result_item_title);
        mFrameLayout = (FrameLayout) findViewById(R.id.frContainerQuestion);
        mFrameLayout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ResultFragment resultFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(RESULT_KEY, (ArrayList<? extends Parcelable>) mResults);
        resultFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frContainerQuestion, resultFragment);
        fragmentTransaction.commit();
    }*/

    private void updateButton(int position) {
        if (position == 0) {
            mBtnPrev.setVisibility(View.GONE);
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
        Collections.shuffle(mQuestions);
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
