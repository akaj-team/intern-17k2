package vn.asiantech.internship.ui.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Thanh Thien on 6/26/2017.
 * Asian Tech
 */
class ViewPagerSecondAdapter extends FragmentStatePagerAdapter {

    private String[] mUrls;

    ViewPagerSecondAdapter(FragmentManager fm, String[] urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerTabChildSecondFragment.newInstance(mUrls[position], position);
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }
}
