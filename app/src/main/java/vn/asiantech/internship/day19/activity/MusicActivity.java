package vn.asiantech.internship.day19.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day19.adapter.OnChooseSongListener;
import vn.asiantech.internship.day19.adapter.SongAdapter;
import vn.asiantech.internship.day19.model.Song;
import vn.asiantech.internship.day19.utils.MusicUtil;
import vn.asiantech.internship.day19.service.Action;
import vn.asiantech.internship.day19.service.SongService;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener, OnChooseSongListener {
    public static final String TYPE_SONGS = "Songs";
    public static final String TYPE_POSITION = "Position";
    public static final String TYPE_CHOOSE_TIME = "chooseTime";
    public static final String TYPE_TIME = "time";
    public static final String TYPE_SECOND = "second";
    public static final String TYPE_ISPLAYING = "isPlaying";

    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnPause;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnShuffleSelected;
    private ImageButton mImgBtnAutoNext;
    private ImageButton mImgBtnAutoNextSelected;
    private TextView mTvTimeTotal;
    private TextView mTvCurrentTime;
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private TextView mTvSongNow;
    private TextView mTvArtistNow;

    private List<Song> mSongs = new ArrayList<>();
    private int mCurrentPlay;
    private SongAdapter mSongAdapter;
    private MyBroadcast mMyBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMusic);
        mSeekBar = (SeekBar) findViewById(R.id.seekBarMusic);
        mTvCurrentTime = (TextView) findViewById(R.id.tvTimeNow);
        mTvTimeTotal = (TextView) findViewById(R.id.tvTimeTotal);
        mTvSongNow = (TextView) findViewById(R.id.tvNowPlaySong);
        mTvArtistNow = (TextView) findViewById(R.id.tvNowPlayArtist);

        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        mImgBtnPause = (ImageButton) findViewById(R.id.imgBtnPause);
        mImgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        mImgBtnShuffleSelected = (ImageButton) findViewById(R.id.imgBtnShuffleSelected);
        mImgBtnAutoNext = (ImageButton) findViewById(R.id.imgBtnAutoNext);
        mImgBtnAutoNextSelected = (ImageButton) findViewById(R.id.imgBtnAutoNextSelected);

        mImgBtnPlay.setOnClickListener(this);
        mImgBtnPause.setOnClickListener(this);
        mImgBtnShuffleSelected.setOnClickListener(this);
        mImgBtnShuffle.setOnClickListener(this);
        mImgBtnAutoNext.setOnClickListener(this);
        mImgBtnAutoNextSelected.setOnClickListener(this);
        findViewById(R.id.imgBtnPrevious).setOnClickListener(this);
        findViewById(R.id.imgBtnNext).setOnClickListener(this);

        // Register Broadcast
        mMyBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SEND_POSITION.getValue());
        intentFilter.addAction(Action.SEEK.getValue());
        intentFilter.addAction(Action.CLOSE_ACTIVITY.getValue());
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mMyBroadcast, intentFilter);

        // Get song information from resource
        String[] songUrls = getApplicationContext().getResources().getStringArray(R.array.song_urls);
        String[] songNames = getApplicationContext().getResources().getStringArray(R.array.song_names);
        String[] songArtists = getApplicationContext().getResources().getStringArray(R.array.song_artists);
        int[] songImages = {
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
                R.mipmap.img_taylor_swift,
        };

        for (int i = 0; i < songUrls.length; i++) {
            mSongs.add(new Song(songNames[i], songArtists[i], songImages[i], songUrls[i], 0));
        }

        // Create RecyclerView
        mSongAdapter = new SongAdapter(mSongs, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mSongAdapter);

        // Send list song to Service
        sendListSong();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // No-op
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No-op
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(MusicActivity.this, SongService.class);
                intent.setAction(Action.SEEK_TO.getValue());
                intent.putExtra(TYPE_CHOOSE_TIME, seekBar.getProgress());
                startService(intent);
            }
        });

        // Hander when click into notification
        if (getIntent().getAction().equals(Action.CLICK_NOTIFICATION.getValue())) {
            boolean check = getIntent().getBooleanExtra(TYPE_ISPLAYING, false);
            if (check) {
                mImgBtnPlay.setVisibility(View.VISIBLE);
                mImgBtnPause.setVisibility(View.INVISIBLE);
            } else {
                mImgBtnPlay.setVisibility(View.INVISIBLE);
                mImgBtnPause.setVisibility(View.VISIBLE);
            }
            sendIntent(Action.CLICK_NOTIFICATION.getValue());
            mCurrentPlay = getIntent().getIntExtra(TYPE_POSITION, 0);
            mSongAdapter.setPosition(mCurrentPlay);
            mRecyclerView.scrollToPosition(mCurrentPlay);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcast);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgBtnPlay:
                mImgBtnPlay.setVisibility(View.INVISIBLE);
                mImgBtnPause.setVisibility(View.VISIBLE);
                sendIntent(Action.PLAY.getValue());
                break;
            case R.id.imgBtnPause:
                mImgBtnPlay.setVisibility(View.VISIBLE);
                mImgBtnPause.setVisibility(View.INVISIBLE);
                sendIntent(Action.PAUSE.getValue());
                break;
            case R.id.imgBtnNext:
                sendIntent(Action.NEXT.getValue());
                mImgBtnPlay.setVisibility(View.VISIBLE);
                mImgBtnPause.setVisibility(View.INVISIBLE);
                mSongAdapter.setPosition(mCurrentPlay);
                mSongAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mCurrentPlay);
                break;
            case R.id.imgBtnShuffle:
                mImgBtnShuffle.setVisibility(View.INVISIBLE);
                mImgBtnShuffleSelected.setVisibility(View.VISIBLE);
                sendIntent(Action.SHUFFLE.getValue());
                break;
            case R.id.imgBtnShuffleSelected:
                mImgBtnShuffle.setVisibility(View.VISIBLE);
                mImgBtnShuffleSelected.setVisibility(View.INVISIBLE);
                sendIntent(Action.SHUFFLE_SELECTED.getValue());
                break;
            case R.id.imgBtnPrevious:
                mImgBtnPlay.setVisibility(View.VISIBLE);
                mImgBtnPause.setVisibility(View.INVISIBLE);
                sendIntent(Action.PREVIOUS.getValue());
                mSongAdapter.setPosition(mCurrentPlay);
                mRecyclerView.scrollToPosition(mCurrentPlay);
                mSongAdapter.notifyDataSetChanged();
                break;
            case R.id.imgBtnAutoNext:
                mImgBtnAutoNext.setVisibility(View.GONE);
                mImgBtnAutoNextSelected.setVisibility(View.VISIBLE);
                sendIntent(Action.AUTO_NEXT.getValue());
                break;
            case R.id.imgBtnAutoNextSelected:
                mImgBtnAutoNext.setVisibility(View.VISIBLE);
                mImgBtnAutoNextSelected.setVisibility(View.INVISIBLE);
                sendIntent(Action.AUTO_NEXT_SELETED.getValue());
                break;
        }
    }

    private void sendIntent(String action) {
        Intent intent = new Intent(this, SongService.class);
        intent.setAction(action);
        startService(intent);
    }

    private void sendListSong() {
        Intent intent = new Intent(this, SongService.class);
        intent.setAction(Action.SONGS.getValue());
        intent.putParcelableArrayListExtra(TYPE_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        startService(intent);
    }

    // Hander when click item on RecyclerView
    @Override
    public void onSongUpdate(int positon) {
        mImgBtnPlay.setVisibility(View.VISIBLE);
        mImgBtnPause.setVisibility(View.INVISIBLE);
        mTvSongNow.setText(mSongs.get(positon).getName());
        mTvArtistNow.setText(mSongs.get(positon).getArtist());
        mSongAdapter.setPosition(positon);
        mSongAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this, SongService.class);
        intent.setAction(Action.CHOOSE_PLAY.getValue());
        intent.putExtra(MusicActivity.TYPE_POSITION, positon);
        startService(intent);
        mRecyclerView.scrollToPosition(mCurrentPlay);
    }

    /**
     * Create MyBroadcast
     */
    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(Action.SEND_POSITION.getValue())) {
                mCurrentPlay = intent.getIntExtra(MusicActivity.TYPE_POSITION, 0);
                mSongAdapter.setPosition(mCurrentPlay);
                mSongAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(mCurrentPlay);
            } else if (s.equals(Action.SEEK.getValue())) {
                processTime(intent);
            } else if (s.equals(Action.CLOSE_ACTIVITY.getValue())) {
                finish();
            }
        }
    }

    // Process seekbar
    private void processTime(Intent intent) {
        if (mMyBroadcast != null) {
            int length = Integer.parseInt(intent.getStringExtra(TYPE_TIME));
            mSeekBar.setMax(length);
            int position = Integer.parseInt(intent.getStringExtra(TYPE_SECOND));
            mCurrentPlay = intent.getIntExtra(TYPE_POSITION, 0);
            mSeekBar.setProgress(position);
            mTvCurrentTime.setText(MusicUtil.showTime(position));
            mTvTimeTotal.setText(MusicUtil.showTime(length));
            mTvSongNow.setText(mSongs.get(mCurrentPlay).getName());
            mTvArtistNow.setText(mSongs.get(mCurrentPlay).getArtist());
        }
    }
}
