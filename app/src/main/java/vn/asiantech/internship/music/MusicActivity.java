package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import vn.asiantech.internship.R;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MusicActivity.class.getSimpleName();
    public static final int STOP_STATUS = 0;
    public static final int PLAY_STATUS = 1;
    public static final int PAUSE_STATUS = 2;
    public static final int SHUFFLE = 0;
    public static final int NO_SHUFFLE = 1;
    public static final int NO_REPEAT = 0;
    public static final int REPEAT = 1;
    public static final int REPEAT_ONE = 2;
    public static final String KEY_POSITION = "position";
    public static final String KEY_CHOOSE_TIME = "choose_time";
    public static final String KEY_PLAY_STATUS = "status";
    public static final String KEY_SHUFFLE_STATUS = "shuffle";
    public static final String KEY_LOOP_STATUS = "loop";
    private TextView mTvCurrentTime;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPrevious;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private ImageView mImgRepeat;
    private int mBtnPlayStatus;
    private int mBtnShuffleStatus;
    private int mBtnRepeatStatus;
    private int mPosition;
    private int mLength = 0;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processTime(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initState();
        initViews();
        setClickButton();
        initIntentFilter();
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTvCurrentTime.setText(getTime(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mTvCurrentTime.setText(getTime(seekBar.getProgress()));
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.putExtra(KEY_CHOOSE_TIME, seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                Log.d(TAG, "onStopTrackingTouch: seek to " + seekBar.getProgress());
                startService(playIntent);
            }
        });
    }

    private void processTime(Intent intent) {
//        if (mLength == 0) {
        mLength = intent.getIntExtra(MusicService.KEY_TIME, 0);
        mSeekBar.setMax(mLength);
        mTvTime.setText(getTime(mLength));
//            mSeekBar.setProgress(0);
//            return;
//        }
        int position = intent.getIntExtra(MusicService.KEY_CURRENT_TIME, 0);
        Log.d(TAG, "processTime: " + position);
        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(getTime(position));
    }

    private void initState() {
        mBtnPlayStatus = STOP_STATUS;
        mBtnShuffleStatus = NO_SHUFFLE;
        mBtnRepeatStatus = NO_REPEAT;
        mPosition = 0;
    }

    private void setClickButton() {
        mImgRepeat.setOnClickListener(this);
        mImgShuffle.setOnClickListener(this);
        mImgPrevious.setOnClickListener(this);
        mImgPlay.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
        mImgRepeat.setOnClickListener(this);
    }

    private void initViews() {
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTime = (TextView) findViewById(R.id.tvTime);
        mImgShuffle = (ImageView) findViewById(R.id.imgShuffle);
        mImgPrevious = (ImageView) findViewById(R.id.imgPrevious);
        mImgPlay = (ImageView) findViewById(R.id.imgPlay);
        mImgNext = (ImageView) findViewById(R.id.imgNext);
        mImgRepeat = (ImageView) findViewById(R.id.imgRepeat);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                if (mBtnPlayStatus == STOP_STATUS) {
                    mBtnPlayStatus = PLAY_STATUS;

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    Intent startIntent = new Intent(this, MusicService.class);
                    startIntent.setAction(Action.START.getValue());
                    startIntent.putExtra(KEY_POSITION, mPosition);
                    startService(startIntent);
                    Log.d(TAG, "onClick: Play");

                    break;
                }
                if (mBtnPlayStatus == PLAY_STATUS) {
                    mBtnPlayStatus = PAUSE_STATUS;

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Intent pauseIntent = new Intent(this, MusicService.class);
                    pauseIntent.setAction(Action.PAUSE.getValue());
                    startService(pauseIntent);
                    Log.d(TAG, "onClick: pause");

                    break;
                }
                if (mBtnPlayStatus == PAUSE_STATUS) {
                    mBtnPlayStatus = PLAY_STATUS;

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    Intent resumeIntent = new Intent(this, MusicService.class);
                    resumeIntent.setAction(Action.RESUME.getValue());
                    startService(resumeIntent);
                    Log.d(TAG, "onClick: resume");

                    break;
                }
            case R.id.imgShuffle:
                if (mBtnShuffleStatus == NO_SHUFFLE) {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_24dp);
                    mBtnShuffleStatus = SHUFFLE;

                } else {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
                    mBtnShuffleStatus = NO_SHUFFLE;
                }
                sendShuffleStatus();
                break;
            case R.id.imgPrevious:
                sendPrevious();
                break;
            case R.id.imgNext:
                sendNext();
                break;
            case R.id.imgRepeat:
                if (mBtnRepeatStatus == NO_REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_red_700_24dp);
                    mBtnRepeatStatus = REPEAT;
                    sendRepeatStatus();
                    break;
                }
                if (mBtnRepeatStatus == REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_one_red_700_24dp);
                    mBtnRepeatStatus = REPEAT_ONE;
                    sendRepeatStatus();
                    break;
                }
                if (mBtnRepeatStatus == REPEAT_ONE) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                    mBtnRepeatStatus = NO_REPEAT;
                    sendRepeatStatus();
                    break;
                }
        }
    }

    private String getTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int second = time % 60;
        return minute < 10 ? ("0" + minute + ":" + ((second < 10) ? "0" + second : second))
                : (minute + ":" + ((second < 10) ? "0" + second : second));
    }

    private void sendNext() {
        Intent nextIntent = new Intent(Action.NEXT.getValue());
        sendBroadcast(nextIntent);
    }

    private void sendPrevious() {
        Intent previousIntent = new Intent(Action.PREVIOUS.getValue());
        sendBroadcast(previousIntent);
    }

    private void sendPlayStatus() {
        Intent intentPlay = new Intent(Action.PLAY.getValue());
        intentPlay.putExtra(KEY_PLAY_STATUS, mBtnPlayStatus);
        sendBroadcast(intentPlay);
    }

    private void sendShuffleStatus() {
        Intent intentShuffle = new Intent(Action.SHUFFLE.getValue());
        intentShuffle.putExtra(KEY_SHUFFLE_STATUS, mBtnShuffleStatus);
        sendBroadcast(intentShuffle);
    }

    private void sendRepeatStatus() {
        Intent intentRepeat = new Intent(Action.REPEAT.getValue());
        intentRepeat.putExtra(KEY_LOOP_STATUS, mBtnRepeatStatus);
        sendBroadcast(intentRepeat);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
