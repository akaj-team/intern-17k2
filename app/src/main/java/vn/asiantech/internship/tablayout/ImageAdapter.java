package vn.asiantech.internship.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by sony on 26/06/2017.
 */
public class ImageAdapter extends FragmentStatePagerAdapter {
    private List<Integer> mImages;

    ImageAdapter(FragmentManager fragmentManager, List<Integer> images) {
        super(fragmentManager);
        mImages = images;
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setData(mImages.get(position));
        return imageFragment;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + (position+1);
    }
}