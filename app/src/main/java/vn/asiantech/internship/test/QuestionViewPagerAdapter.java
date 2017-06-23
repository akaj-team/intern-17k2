package vn.asiantech.internship.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * As a adapter of  question viewPager.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
class QuestionViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Test> mTests;

    QuestionViewPagerAdapter(FragmentManager fm, List<Test> tests) {
        super(fm);
        mTests = tests;
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setData(mTests.get(position));
        return questionFragment;
    }

    @Override
    public int getCount() {
        return mTests.size();
    }
}
