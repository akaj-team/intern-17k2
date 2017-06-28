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
    private int mNumberPage;

    public ViewPagerAdapter(FragmentManager fm, int number) {
        super(fm);
        mNumberPage = number;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TabFirstFragment();
            case 1:
                return new TabSecondFragment();
            case 2:
                return new TabThirdFragment();
            case 3:
                return new TabFourthFragment();
            case 4:
                return new TabFifthFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberPage;
    }

}
