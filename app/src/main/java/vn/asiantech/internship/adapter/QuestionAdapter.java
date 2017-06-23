package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by ducle on 23/06/2017.
 */

public class QuestionAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public QuestionAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        mFragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
