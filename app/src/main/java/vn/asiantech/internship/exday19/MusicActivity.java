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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.exday19.MusicListFragment.mUrls;

/**
 * Created by datbu on 02-07-2017.
 */
public class MusicActivity extends AppCompatActivity implements MusicListAdapter.OnClickMusicItemListener {
    public static final String KEY_MUSIC = "music";
    public static final String KEY_POSITION = "position";
    public static final String PREFERENCES_NAME = "preferences";
    public static final String KEY_REPLAY = "replay";
    public static final String KEY_SHUFFLE = "shuffle";
    public static final String KEY_STATUS = "status";

    public static final int NOT_REPLAY = 0;
    public static final int REPLAY_ONE = 1;
    public static final int REPLAY_ALL = 2;

    private FragmentTransaction mFragmentTransaction;
    private PlayMusicFragment mPlayMusicFragment;
    private MusicListFragment mMusicListFragment;
    private ArrayList<MusicItem> mMusicItems;
    private int mPosition;
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
        for (int i = 0; i < MusicListFragment.mUrls.length; i++) {
            mMusicItems.add(new MusicItem(mUrls[i], MusicListFragment.mSongNames[i], MusicListFragment.mImages[i]));
        }
        if (!mIsServiceRunning) {
            Intent intent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
            intent.putParcelableArrayListExtra(KEY_MUSIC, mMusicItems);
            mIsServiceRunning = true;
            startService(intent);
        }
        Log.d("tag", "onCreate: " + mMusicItems);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mMusicListFragment = new MusicListFragment();
        mPlayMusicFragment = new PlayMusicFragment();
        replaceFragment(mMusicListFragment, false);
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.flMusicList, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getCurrentFragment();
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
                Intent intentStopService = new Intent(MusicActivity.this, NotificationServiceMusic.class);
                stopService(intentStopService);
                mIsServiceRunning = false;
                finish();
                break;
            case R.id.list_item:
                replaceFragment(mMusicListFragment, true);
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
        intentFilter.addAction(Action.STOP_SERVICE.getValue());
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onItemClick(ArrayList<MusicItem> musicItems, int position) {
        mPosition = position;
        mMusicItems = musicItems;
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_POSITION, position);
        bundle.putParcelableArrayList(KEY_MUSIC, musicItems);
        mPlayMusicFragment.setArguments(bundle);
        replaceFragment(mPlayMusicFragment, true);
    }
}
