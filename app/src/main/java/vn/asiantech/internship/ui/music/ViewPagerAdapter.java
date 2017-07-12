package vn.asiantech.internship.ui.music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Thanh Thien on 6/26/2017.
 * Asian Tech
 */
class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private MusicShowListFragment mMusicShowListFragment;
    private MusicPlayFragment mMusicPlayFragment;

    ViewPagerAdapter(FragmentManager fm, MusicPlayFragment musicPlayFragment, MusicShowListFragment musicShowListFragment) {
        super(fm);
        mMusicPlayFragment = musicPlayFragment;
        mMusicShowListFragment = musicShowListFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return mMusicShowListFragment;
        }
        return mMusicPlayFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
