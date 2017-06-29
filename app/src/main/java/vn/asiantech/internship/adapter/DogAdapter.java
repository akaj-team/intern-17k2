package vn.asiantech.internship.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.ItemDogFragment;

/**
 * Created by ducle on 27/06/2017.
 */

public class DogAdapter extends FragmentStatePagerAdapter {
    private List<String> mDogImages;

    public DogAdapter(FragmentManager fm, List<String> dogImages) {
        super(fm);
        mDogImages=dogImages;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDogFragment.newInstance(mDogImages,position);
    }

    @Override
    public int getCount() {
        return mDogImages.size();
    }
}
