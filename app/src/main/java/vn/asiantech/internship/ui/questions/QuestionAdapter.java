package vn.asiantech.internship.ui.questions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.models.Question;

/**
 * Created Thanh Thien root on 6/23/17.
 */
public class QuestionAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions;

    QuestionAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionShowFragment.newInstance(mQuestions.get(position), position);
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

}
