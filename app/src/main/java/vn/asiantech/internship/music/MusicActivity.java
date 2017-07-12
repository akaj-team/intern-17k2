package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * MusicActivity show control
 */
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
    public static final String KEY_REPEAT_STATUS = "loop";
    private TextView mTvCurrentTime;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPrevious;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private ImageView mImgRepeat;
    private int mImgPlayStatus;
    private int mImgShuffleStatus;
    private int mImgRepeatStatus;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private int mPosition;
    private int mLength = 0;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Action.SEEK.getValue())) {
                    processTime(intent);
                }
                if (intent.getAction().equals(Action.FINISH.getValue())) {
                    finish();
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initSharedPreferences();
        initViews();
        initState();
        setClickButton();
        initIntentFilter();
    }

    private void initSharedPreferences() {
        mSharedPreferences = getSharedPreferences("status", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        if (!mSharedPreferences.contains(KEY_PLAY_STATUS)) {
            mEditor.putInt(KEY_PLAY_STATUS, STOP_STATUS);
            mEditor.putInt(KEY_SHUFFLE_STATUS, NO_SHUFFLE);
            mEditor.putInt(KEY_REPEAT_STATUS, NO_REPEAT);
            mEditor.apply();
            mEditor.commit();
        }
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        filter.addAction(Action.FINISH.getValue());
        registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTvCurrentTime.setText(Utils.getTime(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mTvCurrentTime.setText(Utils.getTime(seekBar.getProgress()));
                Intent playIntent = new Intent(MusicActivity.this, MusicService.class);
                playIntent.putExtra(KEY_CHOOSE_TIME, seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                Log.d(TAG, "onStopTrackingTouch: seek to " + seekBar.getProgress());
                startService(playIntent);
            }
        });
    }

    private void processTime(Intent intent) {
        mLength = intent.getIntExtra(MusicService.KEY_TIME, 0);
        mSeekBar.setMax(mLength);
        mTvTime.setText(Utils.getTime(mLength));

        int position = intent.getIntExtra(MusicService.KEY_CURRENT_TIME, 0);
        Log.d(TAG, "processTime: " + position);
        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(Utils.getTime(position));
    }

    private void initState() {
        mImgPlayStatus = mSharedPreferences.getInt(KEY_PLAY_STATUS, STOP_STATUS);
        switch (mImgPlayStatus) {
            case STOP_STATUS:
            case PAUSE_STATUS:
                mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                break;
            case PLAY_STATUS:
                mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
        }
        mImgShuffleStatus = mSharedPreferences.getInt(KEY_SHUFFLE_STATUS, NO_SHUFFLE);
        switch (mImgShuffleStatus) {
            case NO_SHUFFLE:
                mImgShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
                break;
            case SHUFFLE:
                mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_24dp);
                break;
        }
        mImgRepeatStatus = mSharedPreferences.getInt(KEY_REPEAT_STATUS, NO_REPEAT);
        switch (mImgRepeatStatus) {
            case NO_REPEAT:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                break;
            case REPEAT:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_red_700_24dp);
                break;
            case REPEAT_ONE:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_one_red_700_24dp);
                break;
        }
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
        mSeekBar = (SeekBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                if (mImgPlayStatus == STOP_STATUS) {
                    mImgPlayStatus = PLAY_STATUS;
                    mEditor.putInt(KEY_PLAY_STATUS, mImgPlayStatus);

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    Intent startIntent = new Intent(this, MusicService.class);
                    startIntent.setAction(Action.START.getValue());
                    startIntent.putExtra(KEY_POSITION, mPosition);
                    startService(startIntent);
                    Log.d(TAG, "onClick: Play");

                    break;
                }
                if (mImgPlayStatus == PLAY_STATUS) {
                    mImgPlayStatus = PAUSE_STATUS;
                    mEditor.putInt(KEY_PLAY_STATUS, mImgPlayStatus);

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Intent pauseIntent = new Intent(this, MusicService.class);
                    pauseIntent.setAction(Action.PAUSE.getValue());
                    startService(pauseIntent);
                    Log.d(TAG, "onClick: pause");

                    break;
                }
                if (mImgPlayStatus == PAUSE_STATUS) {
                    mImgPlayStatus = PLAY_STATUS;
                    mEditor.putInt(KEY_PLAY_STATUS, mImgPlayStatus);

                    sendPlayStatus();

                    mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    Intent resumeIntent = new Intent(this, MusicService.class);
                    resumeIntent.setAction(Action.RESUME.getValue());
                    startService(resumeIntent);
                    Log.d(TAG, "onClick: resume");

                    break;
                }
            case R.id.imgShuffle:
                if (mImgShuffleStatus == NO_SHUFFLE) {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_24dp);
                    mImgShuffleStatus = SHUFFLE;
                    mEditor.putInt(KEY_SHUFFLE_STATUS, mImgShuffleStatus);

                } else {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
                    mImgShuffleStatus = NO_SHUFFLE;
                    mEditor.putInt(KEY_SHUFFLE_STATUS, mImgShuffleStatus);
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
                if (mImgRepeatStatus == NO_REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_red_700_24dp);
                    mImgRepeatStatus = REPEAT;
                    mEditor.putInt(KEY_REPEAT_STATUS, mImgRepeatStatus);

                    sendRepeatStatus();
                    break;
                }
                if (mImgRepeatStatus == REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_one_red_700_24dp);
                    mImgRepeatStatus = REPEAT_ONE;
                    mEditor.putInt(KEY_REPEAT_STATUS, mImgRepeatStatus);

                    sendRepeatStatus();
                    break;
                }
                if (mImgRepeatStatus == REPEAT_ONE) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                    mImgRepeatStatus = NO_REPEAT;
                    mEditor.putInt(KEY_REPEAT_STATUS, mImgRepeatStatus);

                    sendRepeatStatus();
                    break;
                }
        }
        mEditor.commit();
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
        intentPlay.putExtra(KEY_PLAY_STATUS, mImgPlayStatus);
        sendBroadcast(intentPlay);
    }

    private void sendShuffleStatus() {
        Intent intentShuffle = new Intent(Action.SHUFFLE.getValue());
        intentShuffle.putExtra(KEY_SHUFFLE_STATUS, mImgShuffleStatus);
        sendBroadcast(intentShuffle);
    }

    private void sendRepeatStatus() {
        Intent intentRepeat = new Intent(Action.REPEAT.getValue());
        intentRepeat.putExtra(KEY_REPEAT_STATUS, mImgRepeatStatus);
        sendBroadcast(intentRepeat);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        Intent changeSPIntent = new Intent();
        changeSPIntent.setAction(Action.CHANGE.getValue());
        sendBroadcast(changeSPIntent);
    }

    @Override
    protected void onPause() {
        Intent showNotificationIntent = new Intent(Action.SHOW.getValue());
        sendBroadcast(showNotificationIntent);
        super.onPause();
    }
}
