package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.fragment.ItemAnimalFragment;
import vn.asiantech.internship.fragment.Tab2AnimalFragment;

/**
 * Created by ducle on 26/06/2017.
 */
public class AnimalAdapter extends FragmentStatePagerAdapter {
    private List<String> mAnimalImages;
    private List<String> mDogImages;

    public AnimalAdapter(FragmentManager fm, List<String> animalImages, List<String> dogImages) {
        super(fm);
        mAnimalImages = animalImages;
        mDogImages = dogImages;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 1) {
            fragment = Tab2AnimalFragment.newInstance(mDogImages);
        } else {
            fragment = ItemAnimalFragment.newInstance(mAnimalImages, position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mAnimalImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
