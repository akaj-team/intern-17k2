package vn.asiantech.internship.exday19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;

import static vn.asiantech.internship.R.id.seekBar;

/**
 * Created by datbu on 02-07-2017.
 */
public class PlayMusicFragment extends Fragment implements View.OnClickListener {
    public static final String KEY_PLAYING = "playing";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_CURRENT_POSITION = "current";
    public static final String KEY_SHUFFLE = "shuffle";
    public static final String KEY_REPEAT = "repeat";
    public static final String KEY_URL = "url";
    public static final String KEY_IMAGE = "image";


    private CircleImageView mAlbumArt;
    private ImageView mImgPlay;
    private ImageView mImgPrev;
    private ImageView mImgNext;
    private ImageView mImgShuffle;
    private ImageView mImgRepeat;
    private TextView mTvStart;
    private TextView mTvEnd;
    private double mStartTime;
    private double mStopTime;
    private SeekBar mSeekBar;
    private Uri mUri;
    private String mUrl;
    private String mUrlImage;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler;
    private ArrayList<MusicItem> mMusicItems;
    private Intent mIntent;
    private int mPosition;
    private boolean mIsShuffle;
    private boolean mIsRepeat;
    private int mLength;
    private int mTime;
    private boolean mIsPlaying;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                processTime(intent);
                String action = intent.getAction();
                if (Action.SEEK.getValue().equals(action)) {
                    boolean isPlaying = intent.getBooleanExtra(PlayMusicFragment.KEY_PLAYING, false);
                    if (isPlaying ^ mIsPlaying) {
                        mIsPlaying = isPlaying;
                        Log.d("tag", "onReceive: " + mIsPlaying);
                        if (mIsPlaying) {
                            mImgPlay.setImageResource(R.drawable.pause);
                        } else {
                            mImgPlay.setImageResource(R.drawable.play);
                        }
                    }
                    showTime();
                    return;
                }
                if (Action.START.getValue().equals(action)) {
                    String urlImage = intent.getStringExtra(KEY_IMAGE);
                    Log.d("tag11", "onReceive: image  " + urlImage);
                    if (urlImage != null) {
                        mUrlImage = urlImage;
                        Glide.with(getContext()).load(mUrlImage).into(mAlbumArt);
                        Log.d("tag11", "onReceive: image1  " + mUrlImage);
                    }
                }
                if (Action.COMPLETED.getValue().equals(action)) {
                    mLength = 0;
                    mSeekBar.setProgress(0);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        initView(view);
        initData();
        initIntentFilter();
        initStart();
        initClick();
        return view;
    }

    public void initStart() {
        Intent startIntent = new Intent(getContext(), NotificationServiceMusic.class);
        startIntent.setAction(Action.START.getValue());
        startIntent.putExtra(KEY_URL, mUrl);
        startIntent.putExtra(KEY_IMAGE, mUrlImage);
        startIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
        getContext().startService(startIntent);
        mImgPlay.setImageResource(R.drawable.pause);
    }

    public void initData() {
        mMusicItems = new ArrayList<>();
        mPosition = (int) getArguments().getSerializable(MusicActivity.KEY_POSITION);
        mMusicItems = getArguments().getParcelableArrayList(MusicActivity.KEY_MUSIC);
        initUrl();
    }

    public void initUrl() {
        mUrl = mMusicItems.get(mPosition).getUrl();
        mUri = Uri.parse(mUrl);
        Glide.with(getContext()).load(mUrlImage).into(mAlbumArt);
        mMediaPlayer = MediaPlayer.create(getContext(), mUri);
    }

    public void initView(View view) {
        mMediaPlayer = new MediaPlayer();
        mHandler = new Handler();
        mTvStart = (TextView) view.findViewById(R.id.tvTime);
        mTvEnd = (TextView) view.findViewById(R.id.tvDuration);
        mAlbumArt = (CircleImageView) view.findViewById(R.id.imgAlbumArt);
        mImgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        mImgPrev = (ImageView) view.findViewById(R.id.imgPrevious);
        mImgNext = (ImageView) view.findViewById(R.id.imgNext);
        mImgShuffle = (ImageView) view.findViewById(R.id.imgShuffle);
        mImgRepeat = (ImageView) view.findViewById(R.id.imgRepeat);
        mSeekBar = (SeekBar) view.findViewById(seekBar);
        mIntent = new Intent(getContext(), NotificationServiceMusic.class);
    }

    public void initClick() {
        mImgPlay.setOnClickListener(this);
        mImgPrev.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
        mImgShuffle.setOnClickListener(this);
        mImgRepeat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                if (mIsPlaying) {
                    mIntent.setAction(Action.PAUSE.getValue());
                } else {
                    mIntent.setAction(Action.RESUME.getValue());
                }
                getContext().startService(mIntent);
                break;
            case R.id.imgNext:
                mIntent.setAction(Action.NEXT.getValue());
                getContext().startService(mIntent);
                break;
            case R.id.imgPrevious:
                mIntent.setAction(Action.PREV.getValue());
                getContext().startService(mIntent);
                break;
            case R.id.imgRepeat:
                initRepeat();
                mIntent.setAction(Action.REPEAT.getValue());
                mIntent.putExtra(KEY_REPEAT, mIsRepeat);
                getContext().startService(mIntent);
                break;
            case R.id.imgShuffle:
                initShuffle();
                mIntent.setAction(Action.SHUFFLE.getValue());
                mIntent.putExtra(KEY_SHUFFLE, mIsShuffle);
                getContext().startService(mIntent);
                break;
        }
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Action.SEEK.getValue());
        filter.addAction(Action.COMPLETED.getValue());
        getContext().registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent playIntent = new Intent(getContext(), NotificationServiceMusic.class);
                playIntent.putExtra("chooseTime", seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                getContext().startService(playIntent);
            }
        });
    }

    private void processTime(Intent intent) {
        if (mLength == 0) {
            mLength = intent.getIntExtra(KEY_DURATION, 0);
            mSeekBar.setMax(mLength);
            mSeekBar.setProgress(0);
            return;
        }
        mTime = intent.getIntExtra(KEY_CURRENT_POSITION, 0);
        mSeekBar.setProgress(mTime);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    // Show time
    public void showTime() {
        mStartTime = mMediaPlayer.getCurrentPosition();
        mStopTime = mMediaPlayer.getDuration();
        convertTime();
        mHandler.postDelayed(updateTime, 100);
    }

    // Convert time
    public void convertTime() {
        // Time current position
        long minuteStart = TimeUnit.MILLISECONDS.toMinutes((long) mStartTime);
        long secondsStart = TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mStartTime));
        mTvStart.setText(String.format(getString(R.string.time_format), minuteStart, secondsStart));
        // Total time
        long minuteStop = TimeUnit.MILLISECONDS.toMinutes((long) mStopTime);
        long secondsStop = TimeUnit.MILLISECONDS.toSeconds((long) mStopTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mStopTime));
        mTvEnd.setText(String.format(getString(R.string.time_format), minuteStop, secondsStop));
    }

    // Update song current time
    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            mStartTime = mTime;
            long minuteStart = TimeUnit.MILLISECONDS.toMinutes((long) mStartTime);
            long secondsStart = TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) mStartTime));
            mTvStart.setText(String.format(getString(R.string.time_format), minuteStart, secondsStart));
            mHandler.postDelayed(this, 100);
        }
    };

    public void initShuffle() {
        if (mIsShuffle) {
            mIsShuffle = false;
            mImgShuffle.setImageResource(R.drawable.shuffledf);
        } else {
            // make repeat to true
            mIsShuffle = true;
            mImgShuffle.setImageResource(R.drawable.shufflechg);
        }
    }

    public void initRepeat() {
        if (mIsRepeat) {
            mIsRepeat = false;
            mImgRepeat.setImageResource(R.drawable.repeatdf);
        } else {
            // make repeat to true
            mIsRepeat = true;
            mImgRepeat.setImageResource(R.drawable.repeatchg);
        }
    }
}
