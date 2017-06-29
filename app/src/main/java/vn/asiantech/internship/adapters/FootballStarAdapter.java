package vn.asiantech.internship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragments.FootballStarFragment;
import vn.asiantech.internship.ui.fragments.SpainStarFragment;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/27/2017
 */
public class FootballStarAdapter extends FragmentStatePagerAdapter {

    private int[] mImages = {R.drawable.bg_messi, 0, R.drawable.bg_ronaldo, R.drawable.bg_reus, R.drawable.bg_kaka};
    private String[] mPageTitles = {"Messi", "Spain", "Ronaldo", "Reus", "Kaka"};

    public FootballStarAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return SpainStarFragment.getNewInstance();
        }
        return FootballStarFragment.getNewInstance(mImages[position]);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitles[position];
    }
}
