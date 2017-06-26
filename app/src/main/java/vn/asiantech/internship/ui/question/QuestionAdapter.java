package vn.asiantech.internship.ui.question;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.models.Question;

/**
 * Adapter of ViewPager
 * <p>
 * Created by Hai on 6/24/2017.
 */
class QuestionAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions;

    QuestionAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setQuestion(mQuestions.get(position));
        questionFragment.setPosition(position);
        return questionFragment;
    }

    @Override
    public int getCount() {
        return QuestionActivity.QUESTION_SIZE;
    }
}
