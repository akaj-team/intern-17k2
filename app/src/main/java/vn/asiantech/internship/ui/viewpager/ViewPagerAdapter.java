package vn.asiantech.internship.ui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 *
 * Created by Hai on 6/27/2017.
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int[] mImageResources;
    private String[] mTitles;

    ViewPagerAdapter(FragmentManager fm, int[] imageResources, String[] titles) {
        super(fm);
        mImageResources = imageResources;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setImageResource(mImageResources[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
