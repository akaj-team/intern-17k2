package vn.asiantech.internship.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.SongListAdapter;
import vn.asiantech.internship.models.Action;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;
import vn.asiantech.internship.ui.fragments.MainFragment;
import vn.asiantech.internship.ui.fragments.PlayFragment;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/01/2017
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_SONGS = "songs";
    public static final String KEY_POSITION = "position";
    public static final String PREFERENCES_NAME = "preferences";
    public static final String KEY_REPLAY = "replay";
    public static final String KEY_SHUFFLE = "shuffle";
    public static final String KEY_STATUS = "status";

    public static final int NOT_REPLAY = 0;
    public static final int REPLAY_ONE = 1;
    public static final int REPLAY_ALL = 2;

    private TextView mTvTitle;
    private ImageView mImgCloseFragment;
    private ImageView mImgOpenPlayFragment;

    private ArrayList<Song> mSongs;
    private int mSongPosition;
    private boolean mServiceRunning;
    private boolean mIsPlaying;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Action.SEEK.getValue().equals(action)) {
                    boolean isPlaying = intent.getBooleanExtra(PlayFragment.KEY_PLAYING, false);
                    if (isPlaying ^ mIsPlaying) {
                        mIsPlaying = isPlaying;
                    }
                    return;
                }
                if (Action.SONG_CHANGE.getValue().equals(action)) {
                    int position = intent.getIntExtra(KEY_POSITION, 0);
                    mTvTitle.setText(getString(R.string.song_title, mSongs.get(position).getName(), mSongs.get(position).getSinger()));
                    return;
                }
                if (Action.STOP.getValue().equals(action)) {
                    Intent intentStopService = new Intent(MusicActivity.this, MusicService.class);
                    stopService(intentStopService);
                    mServiceRunning = false;
                    finish();
                }
                if (Action.STOP_SERVICE.getValue().equals(action)) {
                    mIsPlaying = false;
                }

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        mImgCloseFragment = (ImageView) findViewById(R.id.imgClose);
        mImgOpenPlayFragment = (ImageView) findViewById(R.id.imgOpenFragmentPlay);

        mSongs = new ArrayList<>();
        mSongs.add(new Song("Ghen", "Khắc Hưng, ERIK, MIN",
                "http://s1mp3.r9s70.vcdn.vn/3c8c0971d135386b6124/9159096744558092578?key=3qKdyKHSDErkqKtpseAOZw&expires=1499077756&filename=Ghen-Khac-Hung-ERIK-MIN.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1234", "Chi Dân",
                "http://s1mp3.r9s70.vcdn.vn/e7563f83e7c70e9957d6/727637092126384063?key=sxJzN3tIlYfNWku1qMv_yg&expires=1498992279&filename=1234-Chi-Dan.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("Có Em Chờ", "Min, Mr A",
                "http://s1mp3.r9s70.vcdn.vn/3292444e9b0a72542b1b/6792228024536335773?key=HV6NXMQUxqkDQxH3VHkkjQ&expires=1498992337&filename=Co-Em-Cho-Min-MrA.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("Có Điều Gì Sao Không Nói Cùng Anh", "Trung Quân",
                "http://s1mp3.r9s70.vcdn.vn/3151ea8332c7db9982d6/8766360630999056592?key=_ykFI9he1bDD23hET7g5Cg&expires=1499011104&filename=Co-Dieu-Gi-Sao-Khong-Noi-Cung-Anh-Trung-Quan-Idol.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("Anh Thế Giới Và Em", "Hương Tràm",
                "http://s1mp3.r9s70.vcdn.vn/8739deea06aeeff0b6bf/1243431489925204133?key=VJsBok9iksvgF1XPnkfjNg&expires=1499018453&filename=Anh-The-Gioi-Va-Em-Huong-Tram.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1-Ghen", "Khắc Hưng, ERIK, MIN",
                "http://s1mp3.r9s70.vcdn.vn/3c8c0971d135386b6124/9159096744558092578?key=3qKdyKHSDErkqKtpseAOZw&expires=1499077756&filename=Ghen-Khac-Hung-ERIK-MIN.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1-1234", "Chi Dân",
                "http://s1mp3.r9s70.vcdn.vn/e7563f83e7c70e9957d6/727637092126384063?key=sxJzN3tIlYfNWku1qMv_yg&expires=1498992279&filename=1234-Chi-Dan.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1-Có Em Chờ", "Min, Mr A",
                "http://s1mp3.r9s70.vcdn.vn/3292444e9b0a72542b1b/6792228024536335773?key=HV6NXMQUxqkDQxH3VHkkjQ&expires=1498992337&filename=Co-Em-Cho-Min-MrA.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1-Có Điều Gì Sao Không Nói Cùng Anh", "Trung Quân",
                "http://s1mp3.r9s70.vcdn.vn/3151ea8332c7db9982d6/8766360630999056592?key=_ykFI9he1bDD23hET7g5Cg&expires=1499011104&filename=Co-Dieu-Gi-Sao-Khong-Noi-Cung-Anh-Trung-Quan-Idol.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("1-Anh Thế Giới Và Em", "Hương Tràm",
                "http://s1mp3.r9s70.vcdn.vn/8739deea06aeeff0b6bf/1243431489925204133?key=VJsBok9iksvgF1XPnkfjNg&expires=1499018453&filename=Anh-The-Gioi-Va-Em-Huong-Tram.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("2-Ghen", "Khắc Hưng, ERIK, MIN",
                "http://s1mp3.r9s70.vcdn.vn/3c8c0971d135386b6124/9159096744558092578?key=3qKdyKHSDErkqKtpseAOZw&expires=1499077756&filename=Ghen-Khac-Hung-ERIK-MIN.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("2-1234", "Chi Dân",
                "http://s1mp3.r9s70.vcdn.vn/e7563f83e7c70e9957d6/727637092126384063?key=sxJzN3tIlYfNWku1qMv_yg&expires=1498992279&filename=1234-Chi-Dan.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("2-Có Em Chờ", "Min, Mr A",
                "http://s1mp3.r9s70.vcdn.vn/3292444e9b0a72542b1b/6792228024536335773?key=HV6NXMQUxqkDQxH3VHkkjQ&expires=1498992337&filename=Co-Em-Cho-Min-MrA.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("2-Có Điều Gì Sao Không Nói Cùng Anh", "Trung Quân",
                "http://s1mp3.r9s70.vcdn.vn/3151ea8332c7db9982d6/8766360630999056592?key=_ykFI9he1bDD23hET7g5Cg&expires=1499011104&filename=Co-Dieu-Gi-Sao-Khong-Noi-Cung-Anh-Trung-Quan-Idol.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));
        mSongs.add(new Song("2-Anh Thế Giới Và Em", "Hương Tràm",
                "http://s1mp3.r9s70.vcdn.vn/8739deea06aeeff0b6bf/1243431489925204133?key=VJsBok9iksvgF1XPnkfjNg&expires=1499018453&filename=Anh-The-Gioi-Va-Em-Huong-Tram.mp3",
                "https://yt3.ggpht.com/-5qEUb8dKx6U/AAAAAAAAAAI/AAAAAAAAAAA/OocjlzJd-zU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg"));

        MainFragment mainFragment = MainFragment.getNewInstance(mSongs, new SongListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Song song, int position) {
                mSongPosition = position;
                startSong();
            }
        });
        replaceFragment(mainFragment, false);
        String s = getIntent().getStringExtra(KEY_STATUS);
        if ("running".equals(s)) {
            replaceFragment(PlayFragment.getNewInstance(), true);
            mSongPosition = getIntent().getIntExtra("position", -1);
            if (mSongPosition > -1) {
                mTvTitle.setText(getString(R.string.song_title, mSongs.get(mSongPosition).getName(), mSongs.get(mSongPosition).getSinger()));
            } else {
                mTvTitle.setText("");
            }
        } else {
            if (!mServiceRunning) {
                Intent intent = new Intent(MusicActivity.this, MusicService.class);
                intent.putParcelableArrayListExtra(KEY_SONGS, mSongs);
                mServiceRunning = true;
                startService(intent);
            }

        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SEEK.getValue());
        intentFilter.addAction(Action.SONG_CHANGE.getValue());
        intentFilter.addAction(Action.STOP.getValue());
        intentFilter.addAction(Action.STOP_SERVICE.getValue());
        registerReceiver(mReceiver, intentFilter);

        mImgCloseFragment.setOnClickListener(this);
        mImgOpenPlayFragment.setOnClickListener(this);

    }

    private void updateToolbar(Fragment fragment) {
        if (fragment instanceof MainFragment) {
            mTvTitle.setText(getString(R.string.app_name));
            mImgCloseFragment.setVisibility(View.GONE);
            if (mIsPlaying) {
                mImgOpenPlayFragment.setVisibility(View.VISIBLE);
            } else {
                mImgOpenPlayFragment.setVisibility(View.GONE);
            }
            return;
        }
        mImgOpenPlayFragment.setVisibility(View.GONE);
        mImgCloseFragment.setVisibility(View.VISIBLE);
        mTvTitle.setText(getString(R.string.song_title, mSongs.get(mSongPosition).getName(), mSongs.get(mSongPosition).getSinger()));
        mTvTitle.setSelected(true);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.replace(R.id.flMainContent, fragment);
        updateToolbar(fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mIsPlaying) {
            Intent stopService = new Intent(MusicActivity.this, MusicService.class);
            stopService(stopService);
        }
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateToolbar(getSupportFragmentManager().findFragmentById(R.id.flMainContent));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose:
                onBackPressed();
                break;
            case R.id.imgOpenFragmentPlay:
                replaceFragment(PlayFragment.getNewInstance(), true);
                break;
        }
    }

    private void startSong() {
        Intent startIntent = new Intent(MusicActivity.this, MusicService.class);
        startIntent.putExtra(KEY_POSITION, mSongPosition);
        startService(startIntent);
        replaceFragment(PlayFragment.getNewInstance(), true);
    }
}
