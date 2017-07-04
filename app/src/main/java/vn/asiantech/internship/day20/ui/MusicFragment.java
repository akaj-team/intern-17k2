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
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.service.MusicService;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public static final String URL = "http://data03.chiasenhac.com/downloads/1540/2/1539612-330ce8e7/128/One%20Call%20Away%20-%20Charlie%20Puth%20[MP3%20128kbps].mp3";

    public static final String CURRENT_TIME = "current_time";

    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnPrevious;
    private ImageButton mImgBtnNext;
    private CircleImageView mCircleImageViewMusic;
    private SeekBar mSeekBar;
    private TextView mTvMusicTime;
    private TextView mCurrentTime;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CURRENT_TIME)) {
                Log.e("at-dinhvo", "onReceive: " + intent.getAction());
                mTvMusicTime.setText(intToStringDuration(intent.getIntExtra("time", -1)));
                mCurrentTime.setText(intToStringDuration(intent.getIntExtra("second", -1)));
            }
        }
    };

    private String intToStringDuration(int aDuration) {
        String result = "";
        int hours = 0, minutes = 0, seconds = 0;
        hours = aDuration / 3600;
        minutes = (aDuration - hours * 3600) / 60;
        seconds = (aDuration - (hours * 3600 + minutes * 60));
        result = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return result;
    }

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_music, container, false);
        initUI(layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEvents();
    }

    private void addEvents() {
        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPlay = new Intent();
                intentPlay.setAction(MusicService.ACTION_PLAY);
                intentPlay.putExtra("url", URL);
                getActivity().sendBroadcast(intentPlay);
            }
        });

        mImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNext = new Intent();
                intentNext.setAction(MusicService.ACTION_NEXT);
                intentNext.putExtra("url", URL);
                getActivity().sendBroadcast(intentNext);
            }
        });

        mImgBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrevious = new Intent();
                intentPrevious.setAction(MusicService.ACTION_PREVIOUS);
                intentPrevious.putExtra("url", URL);
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
/*
    private void showNotification() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SCREEN_OFF);
        getActivity().sendBroadcast(intent);
    }*/
}
