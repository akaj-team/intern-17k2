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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.model.Song;
import vn.asiantech.internship.day20.service.MusicService;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public static final String CURRENT_TIME = "current_time";

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
    private boolean isPause = false;
    private boolean isPlaying = false;
    private boolean isShuffle = false;
    private boolean isAutoNext = false;
    private ArrayList<Song> mSongs;
    private int mCurrentPosition = -1;
    private int mDuration;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getAction()) {
                    case MusicService.DURATION:
                        mDuration = intent.getIntExtra("timeInt", -1);
                        Log.e("at-dinhvo", "onReceive: duration: " + mDuration);
                        mTvMusicTime.setText(intent.getStringExtra("time"));
                    case CURRENT_TIME:
                        showSeekBar(intent.getIntExtra("secondInt", -1), mDuration);
                        mCurrentTime.setText(intent.getStringExtra("second"));
                        break;
                    case MusicService.SONG_SHUFFLE:
                        Log.e("at-dinhvo", "onReceive: SONG_SHUFFLE");
                        int index = intent.getIntExtra(MusicService.POS_SHUFFLE, -1);
                        if (index != -1) {
                            mTvNameOfSong.setText(mSongs.get(index).getName());
                        } else {
                            Log.e("at-dinhvo", "onReceive: khong nhan duoc");
                        }
                        break;
                }
            }
        }
    };

    private void startAnimation(int duration) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        animation.setDuration(duration / 1000);
        mCircleImageViewMusic.startAnimation(animation);
    }

    private void showSeekBar(int second, int duration) {
        mSeekBar.setMax(100);
        mSeekBar.incrementProgressBy(1);
//        mSeekBar.setProgress(second * 100 / duration);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

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
        startAnimation(mDuration);
    }

    private void getSong() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCurrentPosition = bundle.getInt(MusicActivity.KEY_POS);
            mSongs = bundle.getParcelableArrayList(MusicActivity.KEY_LIST);
        }
        // start the song
        if (mCurrentPosition != -1) {
            Intent intentPlay = new Intent();
            intentPlay.setAction(MusicService.ACTION_PLAY);
            intentPlay.putExtra(MusicActivity.KEY_POS, mCurrentPosition);
            getActivity().sendBroadcast(intentPlay);
            isPlaying = true;
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
                if (isPause) { // if music is pausing
                    intentPlay.setAction(MusicService.ACTION_RESUME);
                    mImgBtnPlay.setBackgroundResource(R.drawable.bg_button_pause);
                    isPlaying = true;
                    isPause = false;
                } else if (isPlaying) { // if music is playing
                    intentPlay.setAction(MusicService.ACTION_PAUSE);
                    mImgBtnPlay.setBackgroundResource(R.drawable.bg_button_play);
                    isPause = true;
                    isPlaying = false;
                } else { // music is starting
                    intentPlay.setAction(MusicService.ACTION_PLAY);
                    if (mCurrentPosition != -1) {
                        intentPlay.putExtra(MusicActivity.KEY_POS, mCurrentPosition);
                    } else {
                        Toast.makeText(getContext(), "Please check your url!", Toast.LENGTH_SHORT).show();
                    }
                    isPlaying = true;
                }
                getActivity().sendBroadcast(intentPlay);
            }
        });

        mImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNext = new Intent();
                intentNext.setAction(MusicService.ACTION_NEXT);
                getActivity().sendBroadcast(intentNext);
                mCurrentPosition = (mCurrentPosition == mSongs.size() - 1) ? 0 : mCurrentPosition + 1;
                mTvNameOfSong.setText(mSongs.get(mCurrentPosition).getName());
            }
        });

        mImgBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrevious = new Intent();
                intentPrevious.setAction(MusicService.ACTION_PREVIOUS);
                getActivity().sendBroadcast(intentPrevious);
                mCurrentPosition = (mCurrentPosition == 0) ? mSongs.size() - 1 : mCurrentPosition - 1;
                mTvNameOfSong.setText(mSongs.get(mCurrentPosition).getName());
            }
        });

        mImgBtnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShuffle = new Intent();
                intentShuffle.setAction(MusicService.ACTION_SHUFFLE);
                getActivity().sendBroadcast(intentShuffle);
                isShuffle = !isShuffle;
                if (isShuffle) {
                    mImgBtnShuffle.setBackgroundResource(R.drawable.ic_shuffle_black);
                } else {
                    mImgBtnShuffle.setBackgroundResource(R.drawable.ic_shuffle_white);
                }
                Log.e("at-dinhvo", "shuffle: " + isShuffle);
            }
        });

        mImgBtnAutoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAutoNext = new Intent();
                intentAutoNext.setAction(MusicService.ACTION_AUTONEXT);
                getActivity().sendBroadcast(intentAutoNext);
                isAutoNext = !isAutoNext;
                if (isAutoNext) {
                    mImgBtnAutoNext.setBackgroundResource(R.drawable.ic_auto_next_white);
                } else {
                    mImgBtnAutoNext.setBackgroundResource(R.drawable.ic_auto_next_blue);
                }
                Log.e("at-dinhvo", "auto_next: " + isAutoNext);
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CURRENT_TIME);
        intentFilter.addAction(MusicService.DURATION);
        intentFilter.addAction(MusicService.SONG_SHUFFLE);
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
