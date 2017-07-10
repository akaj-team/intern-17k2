package vn.asiantech.internship.day20.ui;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.adapter.MusicAdapter;
import vn.asiantech.internship.day20.model.Action;
import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.service.MusicService;

/**
 * MusicActivity contain fragment music
 */
public class MusicActivity extends AppCompatActivity {

    public static final String KEY_POS = "position";
    public static final String KEY_LIST = "list_data";
    private FrameLayout mFrameLayout;
    private RecyclerView mRecyclerView;

    private ArrayList<Song> mSongs;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Action.EXIT.getValue())) {
                stopService(new Intent(MusicActivity.this, MusicService.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initUI();
        registerFilter();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarMusic);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mFrameLayout = (FrameLayout) findViewById(R.id.frContainerMusic);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMusic);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MusicActivity.this));
        addMusicSource();
        startMusicService();
        mRecyclerView.setAdapter(new MusicAdapter(MusicActivity.this, mSongs, new MusicAdapter.OnShowMusicPlayer() {
            @Override
            public void onShowPlayer(int position) {
                mRecyclerView.setVisibility(View.GONE);
                mFrameLayout.setVisibility(View.VISIBLE);
                showMediaPlayer(position);
            }
        }));
        findViewById(R.id.imgBtnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void startMusicService() {
        if (!isMyServiceRunning(MusicService.class)) {
            Intent serviceIntent = new Intent(MusicActivity.this, MusicService.class);
            serviceIntent.putParcelableArrayListExtra(KEY_LIST, mSongs);
            startService(serviceIntent);
        }
    }

    private void showMediaPlayer(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POS, position);
        bundle.putParcelableArrayList(KEY_LIST, mSongs);
        musicFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frContainerMusic, musicFragment);
        fragmentTransaction.commit();
    }

    private void addMusicSource() {
        mSongs = new ArrayList<>();
        mSongs.add(new Song("Attention", "Charlie Puth",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDNNGEETLDJTDGLG",
                "http://i.imgur.com/zGDdKKR.jpg"));
        mSongs.add(new Song("We Don't Talk Anymore", "Charlie Puth",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQAXQGGETLDJTDGLG",
                "http://i.imgur.com/dHtH1kP.jpg"));
        mSongs.add(new Song("Love Yourself", "J-FLA",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQNQJVGJTLDJTDGLG",
                "http://i.imgur.com/sE3bqCO.jpg"));
        mSongs.add(new Song("Shape Of You", "J-FLA",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJNELQQTLDJTDGLG",
                "http://i.imgur.com/P1K1KBD.jpg"));
        mSongs.add(new Song("Don't Let Me Down", "J-FLA",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJVJVAATLDJTDGLG",
                "http://i.imgur.com/t5oc1NR.jpg"));
        mSongs.add(new Song("See You Again", "Charlie Puth",
                "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQDVEXDETLDJTDGLG",
                "http://i.imgur.com/7cPlIs6.jpg"));
    }

    private void registerFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.EXIT.getValue());
        registerReceiver(mBroadcastReceiver, intentFilter);
    }
}
