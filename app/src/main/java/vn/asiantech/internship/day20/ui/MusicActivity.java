package vn.asiantech.internship.day20.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initUI();
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
        mRecyclerView.setAdapter(new MusicAdapter(mSongs, new MusicAdapter.OnShowMusicPlayer() {
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
        mSongs.add(new Song("Maps", "Maroon5", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNAEJVGVQTLDJTDGLG"));
        mSongs.add(new Song("Animals", "Maroon5", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQGQJQNNTLDJTDGLG"));
        mSongs.add(new Song("One More Night", "Maroon5", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNADVGQEGTLDJTDGLG"));
        mSongs.add(new Song("Sugar", "Maroon5", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQGXGJAATLDJTDGLG"));
        mSongs.add(new Song("One Call Away", "Charlie Puth", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQAGDQAXTLDJTDGLG"));
        mSongs.add(new Song("See You Again", "Charlie Puth", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQDVEXDETLDJTDGLG"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
