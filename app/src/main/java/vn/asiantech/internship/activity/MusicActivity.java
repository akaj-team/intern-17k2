package vn.asiantech.internship.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.SongAdapter;
import vn.asiantech.internship.asynctasks.LoadListSongAsyncTask;
import vn.asiantech.internship.fragment.PlayMusicFragment;
import vn.asiantech.internship.fragment.SongFragment;
import vn.asiantech.internship.interfaces.OnUpdateDataListener;
import vn.asiantech.internship.models.Song;

/**
 * Created by ducle on 03/07/2017.
 * MusicActivity show media to play music
 */
public class MusicActivity extends AppCompatActivity implements OnUpdateDataListener, SongAdapter.OnItemClickListener {
    private static final String TAG = MusicActivity.class.getSimpleName();
    private String[] mSongId;
    private ArrayList<Song> mSongs;
    private SongFragment mSongFragment;
    private LoadListSongAsyncTask mLoadListSongAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_music);
        initData();
        mSongFragment = SongFragment.newInstance(mSongs);
        addFragment(mSongFragment, true);
        mLoadListSongAsyncTask.execute(mSongId);
    }

    private void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.replace(R.id.flContain, fragment);
        fragmentTransaction.commit();
        Log.d(TAG, "addFragment: ");
    }

    private void initData() {
        mSongId = new String[]{"IW9AAAEA", "IW89O70O", "IWB6OEFZ", "ZWZ9A80D"};
        mSongs = new ArrayList<>();
        mLoadListSongAsyncTask = new LoadListSongAsyncTask(this);
    }

    @Override
    public void onShowProgressDialog() {
        mSongFragment.showProgressDialog();
    }

    @Override
    public void onAddSong(Song song) {
        mSongFragment.updateListSong(song);
    }

    @Override
    public void onShowFragment() {
        addFragment(mSongFragment, true);
    }

    @Override
    public void onCloseProgressDialog() {
        mSongFragment.closeProgressDialog();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.flContain);
        getFragmentManager().popBackStackImmediate(fragment.getClass().getName(), 0);
    }

    @Override
    public void onReplaceFragment(int position) {
        PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(mSongs, position);
        addFragment(playMusicFragment, true);
    }
}
