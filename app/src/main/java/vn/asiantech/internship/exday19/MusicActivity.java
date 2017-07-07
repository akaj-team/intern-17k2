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

    private static String[] mUrls = {"http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQQVEXVVTLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNVEGEADETLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQLGJLVQTLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDDGVVATLDJTDGLG",
            "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQAXQGGETLDJTDGLG"};
    private static String[] mSongNames = {"Butterfly - BEAST",
            "On Rainy Days - BEAST",
            "12:30' - BEAST",
            "Attention - Charlie Puth",
            "We Don't Talk Anymore - Charlie Puth, Selena Gomez"};
    private static String[] mImages = {"http://goodmomusic.net/wp-content/uploads/2016/06/beast-butterfly-1.png",
            "http://zmp3-photo-fbcrawler-td.zadn.vn/thumb/600_600/covers/1/0/107f98d149a0f3e406a92b349773749b_1305132154.jpg",
            "http://data.whicdn.com/images/146896977/superthumb.jpg",
            "https://images.genius.com/bc29e1ff20b4931dd9919f2ab5252b0e.1000x1000x1.jpg",
            "https://images.genius.com/3bfb705b70df7ba7f0b19ff356fea3b5.1000x1000x1.jpg"};

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
        for (int i = 0; i < mUrls.length; i++) {
            mMusicItems.add(new MusicItem(mUrls[i], mSongNames[i], mImages[i]));
        }
        if (!mIsServiceRunning) {
            Intent intent = new Intent(MusicActivity.this, NotificationServiceMusic.class);
            intent.putParcelableArrayListExtra(KEY_MUSIC, mMusicItems);
            mIsServiceRunning = true;
            startService(intent);
        }
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
//        bundle.putParcelableArrayList(KEY_MUSIC, musicItems);
        mPlayMusicFragment.setArguments(bundle);
        replaceFragment(mPlayMusicFragment, true);
    }
}
