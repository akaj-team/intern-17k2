package vn.asiantech.internship.day16.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.ui.fragment.Tab2Fragment;
import vn.asiantech.internship.day16.ui.fragment.TabFragment;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class ViewPagerOutAdapter extends FragmentStatePagerAdapter {
    private static final int SIZE = 5;
    private int[] mImages = {
            R.mipmap.img_mikasa,
            R.mipmap.img_mik,
            R.mipmap.img_hoataru,
            R.mipmap.img_hotar,
            R.mipmap.img_htr
    };

    public ViewPagerOutAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return Tab2Fragment.init();
            default:
                return TabFragment.init(mImages[position]);
        }
    }

    @Override
    public int getCount() {
        return SIZE;
    }

}
