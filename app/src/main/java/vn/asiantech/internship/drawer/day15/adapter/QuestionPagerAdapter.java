package vn.asiantech.internship.drawer.day15.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.drawer.day15.models.Question;
import vn.asiantech.internship.drawer.day15.ui.QuestionFragment;

/**
 * Created by at-dinhvo on 25/06/2017.
 */
public class QuestionPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Question> mQuestions;

    public QuestionPagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setQuestion(mQuestions.get(position));
        return questionFragment;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
