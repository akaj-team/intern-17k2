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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private MusicItem mMusicItem;
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
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                processTime(intent);
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
        showTime();
        initClick();
        return view;
    }

    public void initStart() {
        Intent startIntent = new Intent(getContext(), NotificationServiceMusic.class);
        startIntent.setAction(Action.START.getValue());
        startIntent.putExtra("url", mUrl);
        startIntent.putExtra("image", mUrlImage);
        getContext().startService(startIntent);
        mImgPlay.setImageResource(R.drawable.pause);
    }

    public void initData() {
        mMusicItems = new ArrayList<>();
        mPosition = (int) getArguments().getSerializable("position");
        mMusicItems = getArguments().getParcelableArrayList(MusicActivity.KEY_MUSIC);
        initUrl();
    }

    public void initUrl() {
        mUrl = mMusicItems.get(mPosition).getUrl();
        mUri = Uri.parse(mUrl);
        mUrlImage = mMusicItems.get(mPosition).getImage();
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
                mIntent.setAction(Action.PLAY.getValue());
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
                mIntent.putExtra(KEY_REPEAT, mIsShuffle);
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
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
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
        if (mMediaPlayer.isPlaying()) {
            mImgPlay.setImageResource(intent.getIntExtra("pause", 0));
        } else {
            mImgPlay.setImageResource(intent.getIntExtra("play", 0));
        }
        if (mLength == 0) {
            mLength = Integer.parseInt(intent.getStringExtra("time"));
            mSeekBar.setMax(mLength);
            mSeekBar.setProgress(0);
            return;
        }
        mTime = Integer.parseInt(intent.getStringExtra("second"));
        mSeekBar.setProgress(mTime);
//        showTime();
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

//    public void initSetImg() {
//        if (mMediaPlayer.isPlaying()) {
//
//        }
//    }

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
            Toast.makeText(getContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
            mImgShuffle.setImageResource(R.drawable.shuffledf);
        } else {
            // make repeat to true
            mIsShuffle = true;
            Toast.makeText(getContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
            mImgShuffle.setImageResource(R.drawable.shufflechg);
        }
    }

    public void initRepeat() {
        if (mIsRepeat) {
            mIsRepeat = false;
            Toast.makeText(getContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
            mImgRepeat.setImageResource(R.drawable.repeatdf);
        } else {
            // make repeat to true
            mIsRepeat = true;
            Toast.makeText(getContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
            mImgRepeat.setImageResource(R.drawable.repeatchg);
        }
    }
}
