package vn.asiantech.internship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.models.Question;
import vn.asiantech.internship.ui.fragments.QuestionFragment;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/23/2017
 */
public class TestAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestionSet;

    public TestAdapter(FragmentManager fm, List<Question> list) {
        super(fm);
        mQuestionSet = list;
    }

    @Override
    public Fragment getItem(final int position) {
        QuestionFragment questionFragment = QuestionFragment.getNewInstance(mQuestionSet.get(position), position);
        questionFragment.setListener(new QuestionFragment.OnDataChanged() {
            @Override
            public void onAnswerChoose(int question, int answer) {
                mQuestionSet.get(position).setUserAnswer(answer);
            }
        });
        return questionFragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
