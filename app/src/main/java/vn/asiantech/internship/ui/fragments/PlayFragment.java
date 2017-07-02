package vn.asiantech.internship.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Action;
import vn.asiantech.internship.ui.main.MusicActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 07/02/2017
 */
public class PlayFragment extends Fragment implements View.OnClickListener {

    public static final String KEY_SEEK = "seek";
    public static final String KEY_PLAYING = "playing";
    public static final String KEY_DURATION = "dur";
    public static final String KEY_CURRENT = "cur";

    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPlay;
    private ImageView mImgReplay;
    private int mSongDuration;
    private boolean mIsPlaying;
    private boolean mIsShuffle;
    private int mReplayType;
    private SharedPreferences mPreferences;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Action.SEEK.getValue().equals(action)) {
                if (mSongDuration == 0) {
                    mSongDuration = intent.getIntExtra(KEY_DURATION, 0);
                    mSeekBar.setMax(mSongDuration);
                }
                boolean isPlaying = intent.getBooleanExtra(KEY_PLAYING, false);
                if (mIsPlaying ^ isPlaying) {
                    mIsPlaying = isPlaying;
                    if (mIsPlaying) {
                        mImgPlay.setImageResource(R.drawable.ic_pause_circle_outline_red_a700_36dp);
                    } else {
                        mImgPlay.setImageResource(R.drawable.ic_play_circle_outline_red_a700_36dp);
                    }
                }
                mSeekBar.setProgress(intent.getIntExtra(KEY_CURRENT, 0));
                return;
            }
            if (Action.SONG_COMPLETED.getValue().equals(action)) {
                mSongDuration = 0;
                mSeekBar.setProgress(0);
            }
        }
    };

    public static PlayFragment getNewInstance() {
        return new PlayFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreferences = getContext().getSharedPreferences(MusicActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mIsShuffle = mPreferences.getBoolean(MusicActivity.KEY_SHUFFLE, false);
        mReplayType = mPreferences.getInt(MusicActivity.KEY_REPLAY, 0);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SEEK.getValue());
        intentFilter.addAction(Action.SONG_COMPLETED.getValue());
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mImgShuffle = (ImageView) view.findViewById(R.id.imgShuffle);
        ImageView imgPrevious = (ImageView) view.findViewById(R.id.imgPrevious);
        mImgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        ImageView imgNext = (ImageView) view.findViewById(R.id.imgNext);
        mImgReplay = (ImageView) view.findViewById(R.id.imgReplay);
        ImageView imgDisk = (ImageView) view.findViewById(R.id.imgDisk);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.clockwise);
        animation.setFillAfter(true);
        imgDisk.startAnimation(animation);

        if (mIsShuffle) {
            mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_36dp);
        }
        switch (mReplayType) {
            case MusicActivity.REPLAY_ONE:
                mImgReplay.setImageResource(R.drawable.ic_replay_1_red_a700_36dp);
                break;
            case MusicActivity.REPLAY_ALL:
                mImgReplay.setImageResource(R.drawable.ic_replay_red_a700_36dp);
                break;
        }

        mImgShuffle.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        mImgPlay.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        mImgReplay.setOnClickListener(this);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Intent intent = new Intent(Action.SEEK_TO.getValue());
                    intent.putExtra(KEY_SEEK, progress);
                    sendMyBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                Intent playIntent = new Intent();
                if (mIsPlaying) {
                    playIntent.setAction(Action.PAUSE.getValue());
                } else {
                    playIntent.setAction(Action.RESUME.getValue());
                }
                sendMyBroadcast(playIntent);
                break;
            case R.id.imgNext:
                Intent nextIntent = new Intent(Action.NEXT_SONG.getValue());
                sendMyBroadcast(nextIntent);
                break;
            case R.id.imgPrevious:
                Intent previousIntent = new Intent(Action.PREVIOUS_SONG.getValue());
                sendMyBroadcast(previousIntent);
                break;
            case R.id.imgShuffle:
                mIsShuffle = !mIsShuffle;
                if (mIsShuffle) {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_36dp);
                } else {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_grey_500_36dp);
                }
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean(MusicActivity.KEY_SHUFFLE, mIsShuffle);
                editor.apply();
                break;
            case R.id.imgReplay:
                mReplayType = (mReplayType + 1) % 3;
                switch (mReplayType) {
                    case MusicActivity.NOT_REPLAY:
                        mImgReplay.setImageResource(R.drawable.ic_replay_grey_500_36dp);
                        break;
                    case MusicActivity.REPLAY_ONE:
                        mImgReplay.setImageResource(R.drawable.ic_replay_1_red_a700_36dp);
                        break;
                    case MusicActivity.REPLAY_ALL:
                        mImgReplay.setImageResource(R.drawable.ic_replay_red_a700_36dp);
                        break;
                }
                SharedPreferences.Editor editor1 = mPreferences.edit();
                editor1.putInt(MusicActivity.KEY_REPLAY, mReplayType);
                editor1.apply();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    public void sendMyBroadcast(Intent intent) {
        getContext().sendBroadcast(intent);
    }
}
