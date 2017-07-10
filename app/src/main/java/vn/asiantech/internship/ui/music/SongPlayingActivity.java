package vn.asiantech.internship.ui.music;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 *
 * Created by quanghai on 02/07/2017.
 */
public class SongPlayingActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView mTvSongName;
    private TextView mTvArtist;
    private CircleImageView mImgDisk;
    private TextView mTvCurrentTime;
    private TextView mTvDuration;
    private ImageView mImgPause;
    private ImageView mImgReplay;
    private ImageView mImgShuffle;
    private SeekBar mSeekBar;

    private boolean mIsPause;
    private boolean mIsShuffle;
    private boolean mIsReplay;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Action.SEEK.getValue())) {
                processTime(intent);
            } else if (intent.getAction().equals(Action.UPDATE_VIEW.getValue())) {
                getSong(intent);
            } else if (intent.getAction().equals(Action.CLOSE.getValue())) {
                Intent stopIntent = new Intent(getApplicationContext(), MusicService.class);
                stopService(stopIntent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);
        initView();
        initIntentFilter();
        Intent intent = getIntent();
        List<Song> songs = intent.getParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue());
        int position = intent.getIntExtra(Action.KEY_BUNDLE_POSITION.getValue(), -1);
        setView(songs.get(position));
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.SEEK.getValue());
        filter.addAction(Action.UPDATE_VIEW.getValue());
        filter.addAction(Action.CLOSE.getValue());
        registerReceiver(mBroadcastReceiver, filter);
    }

    private void initView() {
        mTvSongName = (TextView) findViewById(R.id.tvSongName);
        mTvArtist = (TextView) findViewById(R.id.tvArtist);
        mImgDisk = (CircleImageView) findViewById(R.id.imgDisk);
        mTvCurrentTime = (TextView) findViewById(R.id.tvcurrentTime);
        mTvDuration = (TextView) findViewById(R.id.tvDuration);
        ImageView imgPrevious = (ImageView) findViewById(R.id.imgPreviousSong);
        ImageView imgNext = (ImageView) findViewById(R.id.imgNextSong);
        mImgPause = (ImageView) findViewById(R.id.imgPauseSong);
        mImgShuffle = (ImageView) findViewById(R.id.imgShuffleSong);
        mImgReplay = (ImageView) findViewById(R.id.imgReplay);
        mSeekBar = (SeekBar) findViewById(R.id.seekBarTime);

        imgPrevious.setOnClickListener(this);
        mImgPause.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        mImgShuffle.setOnClickListener(this);
        mImgReplay.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void setView(Song song) {
        mTvSongName.setText(song.getTitle());
        mTvArtist.setText(song.getArtist());
        setImageLoader(this, song, mImgDisk);
    }

    public void setImageLoader(Context context, Song song, CircleImageView imgView) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageLoader.displayImage(song.getImageUrl(), imgView);
    }

    private void processTime(Intent intent) {
        int duration = intent.getIntExtra(Action.KEY_DURATION.getValue(), 0);
        int position = intent.getIntExtra(Action.KEY_CURRENT_TIME.getValue(), 0);
        mSeekBar.setMax(duration);
        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(showTime(position / 1000));
        mTvDuration.setText(showTime(duration / 1000));
    }

    private void getSong(Intent intent) {
        Song song = intent.getParcelableExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue());
        setView(song);
    }

    public String showTime(int duration) {
        int min = (duration / 60);
        int sec = (duration % 60);
        String minute = (min < 10) ? "0" + min + ":" : min + ":";
        String second = (sec < 10) ? "0" + sec : "" + sec;
        return minute + second;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPreviousSong:
                sendSongBroadcast(Action.PREVIOUS_SONG.getValue());
                break;
            case R.id.imgNextSong:
                sendSongBroadcast(Action.NEXT_SONG.getValue());
                break;
            case R.id.imgPauseSong:
                if (mIsPause) {
                    mIsPause = false;
                    mImgPause.setImageResource(R.drawable.ic_pause_black_24dp);
                    sendSongBroadcast(Action.RESUME.getValue());
                    return;
                }
                mIsPause = true;
                mImgPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                sendSongBroadcast(Action.PAUSE.getValue());
                break;
            case R.id.imgShuffleSong:
                mIsShuffle = !mIsShuffle;
                mImgShuffle.setImageResource(mIsShuffle ? R.drawable.ic_shuffle_white_24dp : R.drawable.ic_shuffle_black_24dp);
                Intent shuffleIntent = new Intent(this, MusicService.class);
                shuffleIntent.setAction(Action.SHUFFLE.getValue());
                shuffleIntent.putExtra(Action.SHUFFLE.getValue(), mIsShuffle);
                sendBroadcast(shuffleIntent);
                break;
            case R.id.imgReplay:
                mIsReplay = !mIsReplay;
                mImgReplay.setImageResource(mIsReplay ? R.drawable.ic_autorenew_white_24dp : R.drawable.ic_autorenew_black_24dp);
                Intent autoNextIntent = new Intent(this, MusicService.class);
                autoNextIntent.setAction(Action.REPLAY.getValue());
                autoNextIntent.putExtra(Action.REPLAY.getValue(), mIsReplay);
                sendBroadcast(autoNextIntent);
                break;
        }
    }

    private void sendSongBroadcast(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Intent intent = new Intent(SongPlayingActivity.this, MusicService.class);
        intent.putExtra(Action.SEEK_TO.getValue(), seekBar.getProgress());
        intent.setAction(Action.SEEK_TO.getValue());
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
