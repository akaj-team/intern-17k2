package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display screen play music.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SongFragment.OnGetSongListener {
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrev;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnAuto;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private SeekBar mSeekBar;
    private ViewPager mMusicViewPager;
    private MusicViewPagerAdapter mAdapter;
    private MusicManager mMusicManager;
    private List<Song> mSongs = new ArrayList<>();
    private Song mSong;
    private MusicTime mTime = new MusicTime();
    private int mLength;
    private int mPosition;
    private BroadcastReceiver mSeekBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        mImgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        mImgBtnPrev = (ImageButton) findViewById(R.id.imgBtnPrev);
        mImgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        mImgBtnAuto = (ImageButton) findViewById(R.id.imgBtnAuto);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mMusicViewPager = (ViewPager) findViewById(R.id.musicViewPager);

        mAdapter = new MusicViewPagerAdapter(getSupportFragmentManager());
        mMusicViewPager.setAdapter(mAdapter);

        mMusicManager = new MusicManager(this);
        mSongs.addAll(mMusicManager.getSong());

        mImgBtnPlay.setOnClickListener(this);
        mImgBtnNext.setOnClickListener(this);
        mImgBtnPrev.setOnClickListener(this);
        mImgBtnShuffle.setOnClickListener(this);
        mImgBtnAuto.setOnClickListener(this);
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

        IntentFilter filter = new IntentFilter("seek");
        registerReceiver(mSeekBroadcastReceiver, filter);
        IntentFilter pauseFilter = new IntentFilter("pause");
        registerReceiver(mPauseBroadcastReceiver, pauseFilter);
        IntentFilter playNextFilter = new IntentFilter("playnext");
        registerReceiver(mPlayNextBroadcastReceiver, playNextFilter);

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
            case R.id.imgBtnNext:
                Intent nextIntent = new Intent(MusicActivity.this, MusicService.class);
                nextIntent.setAction("next");
                startService(nextIntent);
                break;
            case R.id.imgBtnPrev:
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
        if (mLength == 0) {
            mLength = Integer.parseInt(intent.getStringExtra("time"));
            mSeekBar.setMax(mLength);
            return;
        }
        mPosition = Integer.parseInt(intent.getStringExtra("second"));
        upDateTime();
    }

    private void upDateTime() {
        mTvTotalTime.setText("" + mTime.milliSecondsToTimer(mLength));
        mTvCurrentTime.setText("" + mTime.milliSecondsToTimer(mPosition));
        mSeekBar.setProgress(mPosition);
    }
}
