package vn.asiantech.internship.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.models.Question;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class QuestionViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestionList;

    public QuestionViewPagerAdapter(FragmentManager fm, List<Question> questionList) {
        super(fm);
        this.mQuestionList = questionList;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mQuestionList.size();
    }
}
