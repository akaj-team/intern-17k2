package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
public class MusicActivity extends AppCompatActivity implements View.OnClickListener, SongsFragment.OnGetSongListener {
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnReplay;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private SeekBar mSeekBar;
    private final List<Song> mSongs = new ArrayList<>();
    private final MusicTime mTime = new MusicTime();
    private int mLength;
    private int mPosition;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Action.START.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
            } else if (TextUtils.equals(action, Action.SEEK.getValue())) {
                processTime(intent);
            } else if (TextUtils.equals(action, Action.PAUSE.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
                mPosition = Integer.parseInt(intent.getStringExtra("second"));
                updateTime();
            } else if (TextUtils.equals(action, Action.AUTONEXT.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
                mPosition = Integer.parseInt(intent.getStringExtra("autoNext"));
                updateTime();
            } else if (TextUtils.equals(action, Action.SHUFFEL.getValue())) {
                mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_red_400_24dp);
            } else if (TextUtils.equals(action, Action.NOTSHUFFEL.getValue())) {
                mImgBtnShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
            } else if (TextUtils.equals(action, Action.REPLAY.getValue())) {
                mImgBtnReplay.setImageResource(R.drawable.ic_autorenew_red_400_24dp);
            } else if (TextUtils.equals(action, Action.NOTREPLAY.getValue())) {
                mImgBtnReplay.setImageResource(R.drawable.ic_autorenew_white_24dp);
            } else if (TextUtils.equals(action, Action.CANCEL.getValue())) {
                finish();
            } else if (TextUtils.equals(action, Action.ISPLAYING.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
            } else if (TextUtils.equals(action, Action.ISPAUSE.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
            } else if (TextUtils.equals(action, Action.CALLING.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_play_circle_filled_white_48dp);
            } else if (TextUtils.equals(action, Action.ENDCALL.getValue())) {
                mImgBtnPlay.setImageResource(R.drawable.ic_pause_circle_filled_white_48dp);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initSongs();
        initView();
        registerBroadcastReceiver();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                final Intent seekIntent = new Intent(Action.SEEKTO.getValue());
                seekIntent.putExtra("chooseTime", seekBar.getProgress());
                sendBroadcast(seekIntent);
                mPosition = seekBar.getProgress();
                updateTime();
            }
        });
    }

    private void initSongs() {
        // Get songs from phone if you want
        SongManager songManager = new SongManager();
        mSongs.addAll(songManager.getListSongOffline(this));

        mSongs.add(new Song("Ghen", "Min", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNXDXGNQETLDJTDGLG", R.drawable.img_ghen));
        mSongs.add(new Song("Shape Of You", "Ed Sheeran", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJGJLNDTLDJTDGLG", R.drawable.img_shape));
        mSongs.add(new Song("Mask Off", "Future", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJAQXNNTLDJTDGLG", R.drawable.img_future));
        mSongs.add(new Song("Stay", "Zedd, Alessia Cara", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJQJLQDTLDJTDGLG", R.drawable.img_stay));
        mSongs.add(new Song("Believer", "Imagine Dragons", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJDQQLVTLDJTDGLG", R.drawable.img_bliever));
        mSongs.add(new Song("Issues", "Julia Michaels", "http://api.mp3.zing.vn/api/mobile/source/song/LGJGTLGNQJLDXGDTLDJTDGLG", R.drawable.img_issue));
    }

    private void initView() {
        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        ImageButton imgBtnPrevious = (ImageButton) findViewById(R.id.imgBtnPrev);
        mImgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        mImgBtnReplay = (ImageButton) findViewById(R.id.imgBtnReplay);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        ViewPager viewPagerMusic = (ViewPager) findViewById(R.id.viewPagerMusic);
        MusicViewPagerAdapter adapter = new MusicViewPagerAdapter(getSupportFragmentManager());
        viewPagerMusic.setAdapter(adapter);

        mImgBtnPlay.setOnClickListener(this);
        imgBtnNext.setOnClickListener(this);
        imgBtnPrevious.setOnClickListener(this);
        mImgBtnShuffle.setOnClickListener(this);
        mImgBtnReplay.setOnClickListener(this);
    }

    private void registerBroadcastReceiver() {
        IntentFilter mStartFilter = new IntentFilter();
        mStartFilter.addAction(Action.START.getValue());
        mStartFilter.addAction(Action.SEEK.getValue());
        mStartFilter.addAction(Action.PAUSE.getValue());
        mStartFilter.addAction(Action.ISPAUSE.getValue());
        mStartFilter.addAction(Action.ISPLAYING.getValue());
        mStartFilter.addAction(Action.AUTONEXT.getValue());
        mStartFilter.addAction(Action.REPLAY.getValue());
        mStartFilter.addAction(Action.NOTREPLAY.getValue());
        mStartFilter.addAction(Action.SHUFFEL.getValue());
        mStartFilter.addAction(Action.NOTSHUFFEL.getValue());
        mStartFilter.addAction(Action.CANCEL.getValue());
        mStartFilter.addAction(Action.CALLING.getValue());
        mStartFilter.addAction(Action.ENDCALL.getValue());
        registerReceiver(mBroadcastReceiver, mStartFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPlay:
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.setAction(Action.PLAY.getValue());
                playIntent.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) mSongs);
                startService(playIntent);
                break;
            case R.id.imgBtnNext:
                Intent nextIntent = new Intent(MusicActivity.this, MusicService.class);
                nextIntent.setAction(Action.NEXT.getValue());
                startService(nextIntent);
                break;
            case R.id.imgBtnPrev:
                Intent prevIntent = new Intent(MusicActivity.this, MusicService.class);
                prevIntent.setAction(Action.PREVIOUS.getValue());
                startService(prevIntent);
                break;
            case R.id.imgBtnShuffle:
                Intent shuffleIntent = new Intent(MusicActivity.this, MusicService.class);
                shuffleIntent.setAction(Action.SHUFFEL.getValue());
                startService(shuffleIntent);
                break;
            case R.id.imgBtnReplay:
                Intent replayIntent = new Intent(MusicActivity.this, MusicService.class);
                replayIntent.setAction(Action.REPLAY.getValue());
                startService(replayIntent);
                break;
        }
    }

    @Override
    public void onGetSong(int position) {
        Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
        playIntent.putExtra("currentSong", position);
        playIntent.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) mSongs);
        playIntent.setAction(Action.CHOOSESONGFROMLIST.getValue());
        startService(playIntent);
    }

    private void processTime(Intent intent) {
        mLength = Integer.parseInt(intent.getStringExtra("time"));
        mSeekBar.setMax(mLength);
        mPosition = Integer.parseInt(intent.getStringExtra("second"));
        updateTime();
    }

    private void updateTime() {
        mTvTotalTime.setText(mTime.milliSecondsToTimer(mLength));
        mTvCurrentTime.setText(mTime.milliSecondsToTimer(mPosition));
        mSeekBar.setProgress(mPosition);
        if (mTvTotalTime.getText().toString().equals(mTvCurrentTime.getText().toString())) {
            Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
            playIntent.setAction(Action.AUTONEXT.getValue());
            startService(playIntent);
        }
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
