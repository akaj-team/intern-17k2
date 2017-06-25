package vn.asiantech.internship.day15.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.day15.model.Question;
import vn.asiantech.internship.day15.ui.fragment.ItemQuizFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 23/06/2017.
 */
public class QuizAdapter extends FragmentStatePagerAdapter {
    private List<Question> mQuestions;

    public QuizAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemQuizFragment.init(mQuestions.get(position));
    }

    @Override
    public int getCount() {
        return 10;
    }
}
