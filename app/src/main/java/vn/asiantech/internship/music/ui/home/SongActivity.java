package vn.asiantech.internship.music.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.music.models.Action;
import vn.asiantech.internship.music.ui.play.PlayFragment;

public class SongActivity extends AppCompatActivity implements SongAdapter.OnItemClickListener {
    public static final int STOP_STATUS = 0;
    public static final int PLAY_STATUS = 1;
    public static final int PAUSE_STATUS = 2;
    public static final int SHUFFLE = 0;
    public static final int NO_SHUFFLE = 1;
    public static final int NO_REPEAT = 0;
    public static final int REPEAT = 1;
    public static final int REPEAT_ONE = 2;
    public static final String KEY_POSITION = "position";
    public static final String KEY_CHOOSE_TIME = "choose_time";
    public static final String KEY_PLAY_STATUS = "status";
    public static final String KEY_SHUFFLE_STATUS = "shuffle";
    public static final String KEY_REPEAT_STATUS = "loop";
    public static final String KEY_SONG = "song";
    private SongListFragment mSongListFragment;
    private PlayFragment mPlayFragment;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Action.FINISH.getValue())) {
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        String fragment = getIntent().getStringExtra("play_fragment");
        int position = getIntent().getIntExtra(SongActivity.KEY_POSITION, 0);
        if (fragment != null) {
            if (fragment.equals("play_fragment")) {
                mPlayFragment = PlayFragment.newInstance(position, false);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContain, mPlayFragment, "play").commit();
            }
        } else {
            mSongListFragment = new SongListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContain, mSongListFragment, "list");
            fragmentTransaction.commit();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.FINISH.getValue());
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onClickItem(int position) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SongActivity.KEY_PLAY_STATUS, SongActivity.PLAY_STATUS);
        editor.apply();
        editor.commit();

        mPlayFragment = PlayFragment.newInstance(position, true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.flContain, mPlayFragment, "play");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (getFragmentManager().findFragmentByTag("list") instanceof SongListFragment) {
            mSongListFragment.mDataLoaded = false;
        }
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
