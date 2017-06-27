package vn.asiantech.internship.ui.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by Hai on 6/27/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int[] mImageResources;

    public ViewPagerAdapter(FragmentManager fm, int[] imageResources) {
        super(fm);
        mImageResources = imageResources;
    }

    @Override
    public Fragment getItem(int i) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.s
        return null;
    }

    @Override
    public int getCount() {
        return mImageResources.length;
    }
}
