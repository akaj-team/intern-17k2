package vn.asiantech.internship.exday19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.exday19.MusicListFragment.URL;

/**
 * Created by datbu on 02-07-2017.
 */
public class MusicActivity extends AppCompatActivity implements MusicListAdapter.OnClickMusicItemListener {
    public static final String KEY_MUSIC = "music";
    public static final String KEY_POSITION = "position";
    public static final String KEY_SEND = "send";

    private FragmentTransaction mFragmentTransaction;
    private PlayMusicFragment mPlayMusicFragment;
    private MusicListFragment mMusicListFragment;
    private ArrayList<MusicItem> mMusicItems;
    private boolean mIsServiceRunning;
    private boolean mIsPlaying;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Action.SEEK.getValue().equals(action)) {
                    boolean isPlaying = intent.getBooleanExtra(PlayMusicFragment.KEY_PLAYING, false);
                    if (isPlaying ^ mIsPlaying) {
                        mIsPlaying = isPlaying;
                    }
                    return;
                }
                if (Action.STOP.getValue().equals(action)) {
                    Intent intentStopService = new Intent(MusicActivity.this, NotificationServiceMusic.class);
                    stopService(intentStopService);
                    mIsServiceRunning = false;
                    finish();
                }
                if (Action.STOP_SERVICE.getValue().equals(action)) {
                    mIsPlaying = false;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intentFilter();
        mMusicItems = new ArrayList<>();
        for (int i = 0; i < MusicListFragment.URL.length; i++) {
            mMusicItems.add(new MusicItem(URL[i], MusicListFragment.SONG_NAME[i], MusicListFragment.IMAGE[i]));
        }
        if (!mIsServiceRunning) {
            Intent intent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
            intent.putParcelableArrayListExtra(KEY_SEND, mMusicItems);
            mIsServiceRunning = true;
            startService(intent);
        }
        mMusicListFragment = new MusicListFragment();
        mPlayMusicFragment = new PlayMusicFragment();
        replaceFragment(mMusicListFragment, false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        mFragmentTransaction.replace(R.id.flMusicList, fragment);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getCurrentFragment();
        replaceFragment(mMusicListFragment, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                onBackPressed();
                onDestroy();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.flMusicList);
    }

    public void intentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SEEK.getValue());
        intentFilter.addAction(Action.STOP.getValue());
        intentFilter.addAction(Action.STOP_SERVICE.getValue());
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onItemClick(ArrayList<MusicItem> musicItems, int position) {
        mMusicItems = musicItems;
        if (!mIsPlaying) {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_POSITION, position);
            bundle.putParcelableArrayList(KEY_MUSIC, musicItems);
            mPlayMusicFragment.setArguments(bundle);
            replaceFragment(mPlayMusicFragment, true);
        } else {
            Intent intent = new Intent(this, NotificationServiceMusic.class);
            intent.setAction(Action.PLAY.getValue());
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_POSITION, position);
            bundle.putParcelableArrayList(KEY_MUSIC, musicItems);
            mPlayMusicFragment.setArguments(bundle);
            replaceFragment(mPlayMusicFragment, true);
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mIsPlaying) {
            Intent intent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
            stopService(intent);
        }
        unregisterReceiver(mReceiver);
    }
}
