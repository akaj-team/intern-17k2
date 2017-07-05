package vn.asiantech.internship.ui.main;

import android.app.ProgressDialog;
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
import vn.asiantech.internship.backgrounds.GetSongAsyncTask;
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
    private ProgressDialog mProgressDialog;

    private ArrayList<Song> mSongs;
    private int mSongPosition;
    private boolean mServiceRunning;
    private boolean mIsPlaying;
    private String[] mSongIds = {"ZW7FC0I7", "ZW80UUCB", "ZW7FE0FC", "ZW79F6A7", "ZW79O8DI", "ZW7FODC9"
            , "ZW78B06A", "ZW78U908", "ZW78I80B", "ZW77F8E0"};

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
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");

        mSongs = new ArrayList<>();

        GetSongAsyncTask task = new GetSongAsyncTask(this, new GetSongAsyncTask.CallBackListener() {
            @Override
            public void onTaskPreExecute() {
                mProgressDialog.show();
            }

            @Override
            public void onTaskCompleted(ArrayList<Song> songs) {
                mSongs = songs;
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
                    mSongPosition = getIntent().getIntExtra(KEY_POSITION, -1);
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
                mProgressDialog.dismiss();
            }
        });
        task.execute(mSongIds);

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

//    Get Local Songs
//    public static ArrayList<Song> findsong(File root) {
//        File[] files = root.listFiles();
//        ArrayList<Song> songList = new ArrayList<>();
//        for (File singleFile : files) {
//            if (singleFile.isDirectory() && !singleFile.isHidden() && !singleFile.getName().equals("Android")) {
//                songList.addAll(findsong(singleFile));
//            } else {
//                if (singleFile.getName().endsWith(".mp3")) {
//                    Song song = new Song(singleFile.getName(), "Cao Cuong Idol", singleFile.getPath(), "kememi");
//                    songList.add(song);
//                }
//            }
//        }
//        return songList;
//    }
}
