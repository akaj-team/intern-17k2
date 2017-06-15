package vn.asiantech.internship.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import vn.asiantech.internship.ui.feed.PageFirstFragment;
import vn.asiantech.internship.ui.feed.PageSecondFragment;

/**
 * Created by anhhuy on 15/06/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int mPageNum;

    public ViewPagerAdapter(FragmentManager fm, int pageNum) {
        super(fm);
        mPageNum = pageNum;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PageFirstFragment();
            case 1:
                return new PageSecondFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageNum;
    }
}
