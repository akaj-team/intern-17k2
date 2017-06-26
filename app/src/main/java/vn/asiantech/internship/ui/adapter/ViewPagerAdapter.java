package vn.asiantech.internship.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.view.TestFragment;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestionList;

    public ViewPagerAdapter(FragmentManager fm, List<Question> questionList) {
        super(fm);
        mQuestionList = questionList;
    }

    @Override
    public Fragment getItem(final int position) {
        TestFragment testFragment = TestFragment.getInstance(mQuestionList.get(position), position);
        testFragment.setListener(new TestFragment.OnDataChange() {
            @Override
            public void onChooseAnswer(int question, int answer) {
                mQuestionList.get(position).setAnswerChoose(answer);
            }
        });
        return testFragment;
    }

    @Override
    public int getCount() {
//        return mQuestionList.size();
        return 10;
    }
}
