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
    private OnQuestionAdapterListener mOnQuestionAdapterListener;

    QuestionAdapter(FragmentManager fm, List<Question> questions, OnQuestionAdapterListener onQuestionAdapterListener) {
        super(fm);
        mQuestions = questions;
        mOnQuestionAdapterListener = onQuestionAdapterListener;
    }

    @Override
    public Fragment getItem(int position) {
        mOnQuestionAdapterListener.setHeader();
        return QuestionShowFragment.newInstance(mQuestions.get(position));
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    /**
     * {@link OnQuestionAdapterListener}
     */
    public interface OnQuestionAdapterListener {
        void setHeader();
    }
}
