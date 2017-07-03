package vn.asiantech.internship.ui.music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 * Created by quanghai on 02/07/2017.
 */
public class SongDetailActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView mTvSongName;
    private TextView mTvArtist;
    private ImageView mImgPause;
    private SeekBar mSeekBar;

    private MusicActivity mActivity;
    private List<Song> mSongs;
    private boolean mIsPlaying;
    private int mCurrentPosition;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processTime(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        initView();
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        registerReceiver(mBroadcastReceiver, filter);
        mActivity = new MusicActivity();
        mSongs = mActivity.getAllSong(this);
        Intent intent = getIntent();
        mCurrentPosition = intent.getIntExtra("position", -1);
        setView();
    }

    private void initView() {
        mTvSongName = (TextView) findViewById(R.id.tvSongName);
        mTvArtist = (TextView) findViewById(R.id.tvArtist);
        ImageView imgPrevious = (ImageView) findViewById(R.id.imgPreviousSong);
        ImageView imgNext = (ImageView) findViewById(R.id.imgNextSong);
        mImgPause = (ImageView) findViewById(R.id.imgPauseSong);
        ImageView imgShuffle = (ImageView) findViewById(R.id.imgShuffleSong);
        ImageView imgAutoNext = (ImageView) findViewById(R.id.imgAutoNext);
        mSeekBar = (SeekBar) findViewById(R.id.seekBarTime);

        imgPrevious.setOnClickListener(this);
        mImgPause.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgShuffle.setOnClickListener(this);
        imgAutoNext.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void setView() {
        Song song = mSongs.get(mCurrentPosition);
        mTvSongName.setText(song.getTitle());
        mTvArtist.setText(song.getArtist());
    }

    private void processTime(Intent intent) {
        int length = intent.getIntExtra("time", 0);
        int position = intent.getIntExtra("second", 0);
        Log.d("xxx", length + "processTime: " + position);
        mSeekBar.setMax(length);
        mSeekBar.setProgress(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPreviousSong:
                if (mCurrentPosition > 0) {
                    mCurrentPosition--;
                    mActivity.intentStartService(this, mCurrentPosition);
                    setView();
                }
                break;
            case R.id.imgNextSong:
                if (mCurrentPosition < mSongs.size() - 1) {
                    mCurrentPosition++;
                    mActivity.intentStartService(this, mCurrentPosition);
                    setView();
                }
                break;
            case R.id.imgPauseSong:
                Intent intent = new Intent(this, MusicService.class);
                if (mIsPlaying) {
                    mImgPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    intent.setAction(Action.PAUSE.getValue());
                    startService(intent);
                    mIsPlaying = false;
                    return;
                }
                mImgPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                intent.setAction(Action.RESUME.getValue());
                startService(intent);
                mIsPlaying = true;
                break;
            case R.id.imgShuffleSong:
                mIsShuffle = !mIsShuffle;
                Log.d("xxx", "onClick: " + mIsShuffle);
                Intent shuffleIntent = new Intent(this, MusicService.class);
                shuffleIntent.setAction(Action.SHUFFLE.getValue());
                shuffleIntent.putExtra("shuffle", mIsShuffle);
                startService(shuffleIntent);
                break;
            case R.id.imgAutoNext:
                mIsAutoNext = !mIsAutoNext;
                Log.d("xxx", "autonext: " + mIsAutoNext);
                Intent autoNextIntent = new Intent(this, MusicService.class);
                autoNextIntent.setAction(Action.AUTO_NEXT.getValue());
                autoNextIntent.putExtra("autonext", mIsAutoNext);
                startService(autoNextIntent);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Intent intent = new Intent(SongDetailActivity.this, MusicService.class);
        intent.putExtra("chooseTime", seekBar.getProgress());
        intent.setAction(Action.SEEK_TO.getValue());
        startService(intent);
    }
}
