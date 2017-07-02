package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.imgBtnNext;
import static vn.asiantech.internship.R.id.imgBtnPrev;
import static vn.asiantech.internship.R.id.seekBar;

/**
 * Used to display screen play music.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SongFragment.OnGetSongListener {
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnAuto;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private SeekBar mSeekBar;
    private List<Song> mSongs = new ArrayList<>();
    private Song mSong;
    private MusicTime mTime = new MusicTime();
    private int mLength;
    private int mPosition;
    private BroadcastReceiver mStartBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
        }
    };
    private BroadcastReceiver mSeekBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processTime(intent);
        }
    };
    private BroadcastReceiver mPauseBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
            mPosition = Integer.parseInt(intent.getStringExtra("second"));
            upDateTime();
        }
    };
    private BroadcastReceiver mPlayNextBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
            mPosition = Integer.parseInt(intent.getStringExtra("next"));
            upDateTime();
        }
    };
    private BroadcastReceiver mNextBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
        }
    };
    private BroadcastReceiver mPreviousBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
        }
    };
    private BroadcastReceiver mIsShuffleBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_red_400_24dp);
        }
    };
    private BroadcastReceiver mNotIsShuffleBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
        }
    };
    private BroadcastReceiver mIsAutoNextBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnAuto.setImageResource(R.drawable.ic_autorenew_red_400_24dp);
        }
    };
    private BroadcastReceiver mNotIsAutoNextBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnAuto.setImageResource(R.drawable.ic_autorenew_white_24dp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        registerBroadcastReceiver();

        MusicManager musicManager = new MusicManager(this);
        mSongs.addAll(musicManager.getSong());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.putExtra("chooseTime", seekBar.getProgress());
                playIntent.setAction("seekto");
                startService(playIntent);
                mPosition = seekBar.getProgress();
                upDateTime();
            }
        });
    }

    private void initView(){
        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        ImageButton imgBtnPrev = (ImageButton) findViewById(R.id.imgBtnPrev);
        mImgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        mImgBtnAuto = (ImageButton) findViewById(R.id.imgBtnAuto);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        mSeekBar = (SeekBar) findViewById(seekBar);
        ViewPager musicViewPager = (ViewPager) findViewById(R.id.musicViewPager);
        MusicViewPagerAdapter adapter = new MusicViewPagerAdapter(getSupportFragmentManager());
        musicViewPager.setAdapter(adapter);

        mImgBtnPlay.setOnClickListener(this);
        imgBtnNext.setOnClickListener(this);
        imgBtnPrev.setOnClickListener(this);
        mImgBtnShuffle.setOnClickListener(this);
        mImgBtnAuto.setOnClickListener(this);
    }

    private void registerBroadcastReceiver(){
        IntentFilter mStartFilter = new IntentFilter("start");
        registerReceiver(mStartBroadcastReceiver, mStartFilter);
        IntentFilter seekFilter = new IntentFilter("seek");
        registerReceiver(mSeekBroadcastReceiver, seekFilter);
        IntentFilter pauseFilter = new IntentFilter("pause");
        registerReceiver(mPauseBroadcastReceiver, pauseFilter);
        IntentFilter playNextFilter = new IntentFilter("playnext");
        registerReceiver(mPlayNextBroadcastReceiver, playNextFilter);
        IntentFilter isShuffleFilter = new IntentFilter("isShuffle");
        registerReceiver(mIsShuffleBroadcastReceiver, isShuffleFilter);
        IntentFilter notIsShuffleFilter = new IntentFilter("!isShuffle");
        registerReceiver(mNotIsShuffleBroadcastReceiver, notIsShuffleFilter);
        IntentFilter isAutoNextFilter = new IntentFilter("isAutoNext");
        registerReceiver(mIsAutoNextBroadcastReceiver, isAutoNextFilter);
        IntentFilter notIsAutoNextFilter = new IntentFilter("!isAutoNext");
        registerReceiver(mNotIsAutoNextBroadcastReceiver, notIsAutoNextFilter);
        IntentFilter nextFilter = new IntentFilter("next");
        registerReceiver(mNextBroadcastReceiver, nextFilter);
        IntentFilter previousFilter = new IntentFilter("previous");
        registerReceiver(mPreviousBroadcastReceiver, previousFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPlay:
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.setAction("play");
                playIntent.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) mSongs);
                startService(playIntent);
                break;
            case imgBtnNext:
                Intent nextIntent = new Intent(MusicActivity.this, MusicService.class);
                nextIntent.setAction("next");
                startService(nextIntent);
                break;
            case imgBtnPrev:
                Intent prevIntent = new Intent(MusicActivity.this, MusicService.class);
                prevIntent.setAction("previous");
                startService(prevIntent);
                break;
            case R.id.imgBtnShuffle:
                Intent shuffleIntent = new Intent(MusicActivity.this, MusicService.class);
                shuffleIntent.setAction("shuffle");
                startService(shuffleIntent);
                break;
            case R.id.imgBtnAuto:
                Intent autoIntent = new Intent(MusicActivity.this, MusicService.class);
                autoIntent.setAction("autoNext");
                startService(autoIntent);
                break;
        }
    }

    @Override
    public void onGetSong(Song song, int position) {
        mSong = song;
    }

    private void processTime(Intent intent) {
        mLength = Integer.parseInt(intent.getStringExtra("time"));
        mSeekBar.setMax(mLength);
        mPosition = Integer.parseInt(intent.getStringExtra("second"));
        upDateTime();
    }

    private void upDateTime() {
        mTvTotalTime.setText("" + mTime.milliSecondsToTimer(mLength));
        mTvCurrentTime.setText("" + mTime.milliSecondsToTimer(mPosition));
        mSeekBar.setProgress(mPosition);
        if (mTvTotalTime.getText().toString().equals(mTvCurrentTime.getText().toString())) {
            Log.d("hehehe", "upDateTime: ");
            Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
            playIntent.setAction("nextMusic");
            startService(playIntent);
        }
    }
}
