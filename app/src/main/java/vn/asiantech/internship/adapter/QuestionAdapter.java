package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.fragment.QuestionItemFragment;
import vn.asiantech.internship.models.Question;

/**
 * Created by ducle on 23/06/2017.
 */

public class QuestionAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions;

    public QuestionAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionItemFragment.newInstance(mQuestions.get(position));
    }

    @Override
    public int getCount() {
        return 10;
    }
}
