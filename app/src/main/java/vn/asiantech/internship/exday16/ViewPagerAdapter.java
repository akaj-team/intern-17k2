package vn.asiantech.internship.exday16;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


/**
 * Created by datbu on 26-06-2017.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> mImages;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ItemImageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
