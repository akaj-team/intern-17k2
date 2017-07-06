package vn.asiantech.internship.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.asiantech.internship.ui.tablayout.TabFifthFragment;
import vn.asiantech.internship.ui.tablayout.TabFirstFragment;
import vn.asiantech.internship.ui.tablayout.TabFourthFragment;
import vn.asiantech.internship.ui.tablayout.TabSecondFragment;
import vn.asiantech.internship.ui.tablayout.TabThirdFragment;

/**
 * Adapter for five tab layout.
 * Created by AnhHuy on 26-Jun-17.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final int mNumberPage;

    public ViewPagerAdapter(FragmentManager fm, int number) {
        super(fm);
        mNumberPage = number;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TabFirstFragment.newInstance();
            case 1:
                return TabSecondFragment.newInstance();
            case 2:
                return TabThirdFragment.newInstance();
            case 3:
                return TabFourthFragment.newInstance();
            case 4:
                return TabFifthFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberPage;
    }

}
