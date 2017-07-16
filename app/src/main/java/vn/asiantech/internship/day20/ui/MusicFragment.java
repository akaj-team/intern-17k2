package vn.asiantech.internship.day20.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Action;
import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.service.MusicService;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public static final String CURRENT_TIME = "current_time";
    public static final String SONG_NEXT = "song_next";
    public static final String SONG_PREVIOUS = "song_previous";

    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnPrevious;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnShuffle;
    private ImageButton mImgBtnAutoNext;
    private TextView mTvNameOfSong;
    private CircleImageView mCircleImageViewMusic;
    private SeekBar mSeekBar;
    private TextView mTvMusicTime;
    private TextView mCurrentTime;

    private Animation mAnimation;
    private boolean mIsPause;
    private boolean mIsPlaying;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;
    private List<Song> mSongs;
    private int mCurrentPosition = -1;
    private int mDuration;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getAction()) {
                    case MusicService.DURATION:
                        mDuration = intent.getIntExtra(MusicService.KEY_TIME_INT, -1);
                        mTvMusicTime.setText(showTime(mDuration));
                        if (mDuration != -1) {
                            startAnimation(mDuration);
                        }
                        break;
                    case CURRENT_TIME:
                        showSeekBar(intent.getIntExtra("secondInt", -1), mDuration);
                        mCurrentTime.setText(showTime(intent.getIntExtra("secondInt", -1)));
                        break;
                    case SONG_NEXT:
                        int indexNext = intent.getIntExtra(MusicService.POS_DATA, -1);
                        if (indexNext != -1) {
                            mTvNameOfSong.setText(mSongs.get(indexNext).getName());
                            resetPlayFlag();
                        } else {
                            Log.e(MusicService.TAG, "song_next: khong nhan duoc");
                        }
                        break;
                    case SONG_PREVIOUS:
                        int indexPrev = intent.getIntExtra(MusicService.POS_DATA, -1);
                        if (indexPrev != -1) {
                            mTvNameOfSong.setText(mSongs.get(indexPrev).getName());
                            resetPlayFlag();
                        } else {
                            Log.e(MusicService.TAG, "song_previous: khong nhan duoc");
                        }
                        break;
                }
            }
        }
    };

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_music, container, false);
        initUI(layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSong();
        addEvents();
    }

    private void resetPlayFlag() {
        mIsPause = false;
        mIsPlaying = true;
        mImgBtnPlay.setBackgroundResource(R.drawable.bg_button_pause);
    }

    private String showTime(int duration) {
        int min = duration / 60;
        int sec = duration % 60;
        String minute = (min < 10) ? "0" + min + ":" : min + ":";
        String second = (sec < 10) ? "0" + sec : "" + sec;
        return minute + second;
    }

    private void startAnimation(int duration) {
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        mAnimation.setDuration(duration * 1000);
        mCircleImageViewMusic.startAnimation(mAnimation);
    }

    private void showSeekBar(int second, int duration) {
        mSeekBar.setMax(100);
        mSeekBar.incrementProgressBy(1);
        if (duration == 0) {
            mSeekBar.setProgress(0);
        } else {
            mSeekBar.setProgress(second * 100 / duration);
        }
    }

    private void getSong() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCurrentPosition = bundle.getInt(MusicActivity.KEY_POS);
            mSongs = bundle.getParcelableArrayList(MusicActivity.KEY_LIST);
        }
        if (mCurrentPosition != -1) {
            Intent intentPlay = new Intent();
            intentPlay.setAction(Action.PLAY.getValue());
            intentPlay.putExtra(MusicActivity.KEY_POS, mCurrentPosition);
            getActivity().sendBroadcast(intentPlay);
            mIsPlaying = true;
            mTvNameOfSong.setText(mSongs.get(mCurrentPosition).getName());
        } else {
            Toast.makeText(getContext(), "Position is wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {
        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPlay = new Intent();
                if (mIsPause) { // If music is pausing
                    intentPlay.setAction(Action.RESUME.getValue());
                    mImgBtnPlay.setBackgroundResource(R.drawable.bg_button_pause);
                    mIsPlaying = true;
                    mIsPause = false;
                } else if (mIsPlaying) { // If music is playing
                    intentPlay.setAction(Action.PAUSE.getValue());
                    mImgBtnPlay.setBackgroundResource(R.drawable.bg_button_play);
                    mIsPause = true;
                    mIsPlaying = false;
                } else { // Music is starting
                    intentPlay.setAction(Action.PLAY.getValue());
                    if (mCurrentPosition != -1) {
                        intentPlay.putExtra(MusicActivity.KEY_POS, mCurrentPosition);
                    } else {
                        Toast.makeText(getContext(), "Please check your url!", Toast.LENGTH_SHORT).show();
                    }
                    mIsPlaying = true;
                }
                getActivity().sendBroadcast(intentPlay);
            }
        });
        mImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNext = new Intent();
                intentNext.setAction(Action.NEXT.getValue());
                getActivity().sendBroadcast(intentNext);
            }
        });
        mImgBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrevious = new Intent();
                intentPrevious.setAction(Action.PREVIOUS.getValue());
                getActivity().sendBroadcast(intentPrevious);
            }
        });
        mImgBtnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShuffle = new Intent();
                intentShuffle.setAction(Action.SHUFFLE.getValue());
                getActivity().sendBroadcast(intentShuffle);
                mIsShuffle = !mIsShuffle;
                if (mIsShuffle) {
                    mImgBtnShuffle.setBackgroundResource(R.drawable.ic_shuffle_cyan_a700_48dp);
                } else {
                    mImgBtnShuffle.setBackgroundResource(R.drawable.ic_shuffle_white_48dp);
                }
            }
        });
        mImgBtnAutoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAutoNext = new Intent();
                intentAutoNext.setAction(Action.AUTO_NEXT.getValue());
                getActivity().sendBroadcast(intentAutoNext);
                mIsAutoNext = !mIsAutoNext;
                if (mIsAutoNext) {
                    mImgBtnAutoNext.setBackgroundResource(R.drawable.ic_loop_cyan_a700_48dp);
                } else {
                    mImgBtnAutoNext.setBackgroundResource(R.drawable.ic_loop_white_48dp);
                }
            }
        });
    }

    private void initUI(View layout) {
        mImgBtnPlay = (ImageButton) layout.findViewById(R.id.imgBtnPlay);
        mImgBtnNext = (ImageButton) layout.findViewById(R.id.imgBtnNext);
        mImgBtnPrevious = (ImageButton) layout.findViewById(R.id.imgBtnPrevious);
        mImgBtnShuffle = (ImageButton) layout.findViewById(R.id.imgBtnShuffle);
        mImgBtnAutoNext = (ImageButton) layout.findViewById(R.id.imgBtnAutoNext);
        mSeekBar = (SeekBar) layout.findViewById(R.id.seekBar);
        mCircleImageViewMusic = (CircleImageView) layout.findViewById(R.id.imgMusic);
        mCurrentTime = (TextView) layout.findViewById(R.id.tvCurrentTime);
        mTvMusicTime = (TextView) layout.findViewById(R.id.tvTimeSong);
        mTvNameOfSong = (TextView) layout.findViewById(R.id.tvNameOfSong);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFilterReceiver();
    }

    private void setFilterReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CURRENT_TIME);
        intentFilter.addAction(MusicService.DURATION);
        intentFilter.addAction(SONG_PREVIOUS);
        intentFilter.addAction(SONG_NEXT);
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }
}
