package vn.asiantech.internship.ui.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Thanh Thien on 6/26/2017.
 * Asian Tech
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mUrls;

    ViewPagerAdapter(FragmentManager fm, String[] urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return ViewPagerSecondFragment.newInstance();
        }
        return ViewPagerTabChildFragment.newInstance(mUrls[position]);
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
