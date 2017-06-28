package vn.asiantech.internship.exday16;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Created by datbu on 26-06-2017.
 */
public class ViewPagerBdapter extends FragmentStatePagerAdapter {
    private List<String> mImages;

    public ViewPagerBdapter(FragmentManager fm, List<String> images) {
        super(fm);
        mImages = images;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemImageBFragment.newInstance(mImages, position);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int pos = position + 1;
        return "Picture " + pos;
    }
}
