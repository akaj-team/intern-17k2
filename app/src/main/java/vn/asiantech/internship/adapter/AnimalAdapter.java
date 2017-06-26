package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import vn.asiantech.internship.fragment.ItemAnimalFragment;

/**
 * Created by ducle on 26/06/2017.
 */
public class AnimalAdapter extends FragmentStatePagerAdapter {
    private List<String> mAnimalImages;

    public AnimalAdapter(FragmentManager fm, List<String> animalImages) {
        super(fm);
        mAnimalImages = animalImages;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ItemAnimalFragment.newInstance(mAnimalImages, position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
