package vn.asiantech.internship.day20.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private CircleImageView mCircleImageViewMusic;
    private SeekBar mSeekBar;
    private TextView mTvMusicTime;
    private TextView mCurrentTime;
    private boolean isPause = false;
    private boolean isPlaying = false;
    private ArrayList<Song> mSongs;
    private int mCurrentPosition = -1;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CURRENT_TIME)) {
                int duration = intent.getIntExtra("time", -1);
                int second = intent.getIntExtra("second", -1);
                showSeekBar(second, duration);
                mTvMusicTime.setText(showTime(duration / 1000));
                mCurrentTime.setText(showTime(second / 1000));
            }
        }
    };

    private String showTime(int duration) {
        int min = duration / 60;
        int sec = duration % 60;
        String minute = (min < 10) ? "0" + min + ":" : min + ":";
        String second = (sec < 10) ? "0" + sec : "" + sec;
        return minute + second;
    }

    private void showSeekBar(int second, int duration) {
        mSeekBar.setProgress(second * 100 / duration);
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
            }
        });

        mImgBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrevious = new Intent();
                intentPrevious.setAction(MusicService.ACTION_PREVIOUS);
                getActivity().sendBroadcast(intentPrevious);
            }
        });
    }

    private void initUI(View layout) {
        mImgBtnPlay = (ImageButton) layout.findViewById(R.id.imgBtnPlay);
        mImgBtnNext = (ImageButton) layout.findViewById(R.id.imgBtnNext);
        mImgBtnPrevious = (ImageButton) layout.findViewById(R.id.imgBtnPrevious);
        mCircleImageViewMusic = (CircleImageView) layout.findViewById(R.id.imgMusic);
        mSeekBar = (SeekBar) layout.findViewById(R.id.seekBar);
        mCurrentTime = (TextView) layout.findViewById(R.id.tvCurrentTime);
        mTvMusicTime = (TextView) layout.findViewById(R.id.tvTimeSong);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CURRENT_TIME);
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }
}
