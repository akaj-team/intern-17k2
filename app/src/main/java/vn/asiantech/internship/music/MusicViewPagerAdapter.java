package vn.asiantech.internship.music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * As an adapter for music viewPager.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
class MusicViewPagerAdapter extends FragmentStatePagerAdapter {

    MusicViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MainFragment.newInstance();
        } else if (position == 1) {
            return SongFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
