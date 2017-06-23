package vn.asiantech.internship.day15.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.day15.model.Answer;
import vn.asiantech.internship.day15.model.Question;
import vn.asiantech.internship.day15.ui.fragment.ItemQuizFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */

public class QuizAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions;
    private List<Answer> mAnswers;

    public QuizAdapter(FragmentManager fm, List<Question> questions, List<Answer> answers) {
        super(fm);
        mQuestions = questions;
        mAnswers=answers;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemQuizFragment.init(mQuestions.get(position),mAnswers.get(position));
    }

    @Override
    public int getCount() {
        return 10;
    }
}
