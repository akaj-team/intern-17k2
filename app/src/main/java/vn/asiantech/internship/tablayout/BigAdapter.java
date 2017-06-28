package vn.asiantech.internship.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Used to collect and display fragment on viewpagerA.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
class BigAdapter extends FragmentStatePagerAdapter {
    private final List<Integer> mImages;

    BigAdapter(FragmentManager fragmentManager, List<Integer> images) {
        super(fragmentManager);
        mImages = images;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 2) {
            return CenterFragment.newInstance();
        } else {
            return ImageFragment.newInstance(mImages.get(position));
        }
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Search";
            case 1:
                return "Discover";
            case 2:
                return "HoiAn Feed";
            case 3:
                return "Voucher";
            case 4:
                return "User Profile";
        }
        return "";
    }
}
