package vn.asiantech.internship.day20.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public static final String URL = "http://mp3.zing.vn/bai-hat/Tu-Ngay-Em-Den-Da-LAB/ZW80IUAA.html";

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
            if (intent.getAction().equals("CURRENT_TIME")) {
                // TODO: 30/06/2017
            }
        }
    };

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
                intentNext.setAction(MusicService.ACTION_PLAY);
                intentNext.putExtra("url", URL);
                getActivity().sendBroadcast(intentNext);
            }
        });

        mImgBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPrevious = new Intent();
                intentPrevious.setAction(MusicService.ACTION_PLAY);
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
}
