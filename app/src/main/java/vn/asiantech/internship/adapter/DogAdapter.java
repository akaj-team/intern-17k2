package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.fragment.ItemDogFragment;

/**
 * Created by ducle on 27/06/2017.
 * DogAdapter is adapter for sub viewpager
 */
public class DogAdapter extends FragmentStatePagerAdapter {
    private List<String> mDogImages;

    public DogAdapter(FragmentManager fm, List<String> dogImages) {
        super(fm);
        mDogImages = dogImages;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDogFragment.newInstance(mDogImages, position);
    }

    @Override
    public int getCount() {
        return mDogImages.size();
    }
}
