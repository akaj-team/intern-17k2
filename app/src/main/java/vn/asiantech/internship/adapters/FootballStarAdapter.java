package vn.asiantech.internship.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragments.FootballStarFragment;
import vn.asiantech.internship.ui.fragments.SpainStarFragment;

/**
 * Created by PC on 6/26/2017.
 */

public class FootballStarAdapter extends FragmentStatePagerAdapter {

    private int[] mIds = {R.drawable.bg_messi, 0, R.drawable.bg_ronaldo, R.drawable.bg_reus, R.drawable.bg_kaka};

    public FootballStarAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) return SpainStarFragment.getNewInstance();
        return FootballStarFragment.getNewInstance(mIds[position]);
    }
}
