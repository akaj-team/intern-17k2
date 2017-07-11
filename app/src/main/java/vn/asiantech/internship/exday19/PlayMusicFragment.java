package vn.asiantech.internship.exday19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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
    private SeekBar mSeekBar;
    private Uri mUri;
    private String mUrl;
    private String mUrlImage;
    private MediaPlayer mMediaPlayer;
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
                    return;
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
        if (!mIsPlaying) {
            Intent startIntent = new Intent(getContext(), NotificationServiceMusic.class);
            startIntent.setAction(Action.START.getValue());
            startIntent.putExtra(KEY_URL, mUrl);
            startIntent.putExtra(KEY_IMAGE, mUrlImage);
            startIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
            getContext().startService(startIntent);
            mImgPlay.setImageResource(R.drawable.pause);
        } else {
            Intent startIntent = new Intent(getContext(), NotificationServiceMusic.class);
            startIntent.setAction(Action.PLAY.getValue());
            startIntent.putExtra(KEY_URL, mUrl);
            startIntent.putExtra(KEY_IMAGE, mUrlImage);
            startIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
            getContext().startService(startIntent);
            mImgPlay.setImageResource(R.drawable.pause);
        }
    }

    public void initData() {
        mMusicItems = new ArrayList<>();
        mPosition = (int) getArguments().getSerializable(MusicActivity.KEY_POSITION);
        mMusicItems = getArguments().getParcelableArrayList(MusicActivity.KEY_MUSIC);
        mTvStart.setText(R.string.tv_time);
        mTvEnd.setText(R.string.tv_time);
        initAnimation();
    }

    public void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_disk);
        animation.setFillAfter(true);
        mAlbumArt.startAnimation(animation);
    }

    public void initView(View view) {
        mMediaPlayer = new MediaPlayer();
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
        mLength = intent.getIntExtra(KEY_DURATION, 0);
        mTvEnd.setText(miliSecondToString(mLength));
        mSeekBar.setMax(mLength);
        mSeekBar.setProgress(0);
        mTime = intent.getIntExtra(KEY_CURRENT_POSITION, 0);
        mTvStart.setText(miliSecondToString(mTime));
        mSeekBar.setProgress(mTime);
        mUrlImage = intent.getStringExtra(KEY_IMAGE);
        Log.d("tag22", "onReceive:112 " + mUrlImage);
        Glide.with(getContext()).load(mUrlImage).into(mAlbumArt);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    private static String miliSecondToString(int millisecond) {
        millisecond /= 1000;
        return ((millisecond / 60) < 10 ? "0" : "") + (millisecond / 60) + ":" + ((millisecond % 60) < 10 ? "0" : "") + (millisecond % 60);
    }

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
