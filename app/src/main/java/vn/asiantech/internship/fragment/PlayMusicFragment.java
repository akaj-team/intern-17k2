package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Action;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.services.MusicService;

/**
 * Created by ducle on 05/07/2017.
 */

public class PlayMusicFragment extends Fragment {
    private static final String KEY_SONGS = "songs";
    private static final String KEY_POSITION = "position";
    public static final String KEY_URL = "url";
    private static final String TAG = PlayMusicFragment.class.getSimpleName();
    private static final String KEY_START = "start";
    private TextView mTvCurrentTime;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPrevious;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private ImageView mImgLoop;
    private ArrayList<Song> mSongs;
    private int mPosition;
    private int mLength = 0;
    private Intent mIntent;
    private BroadcastReceiver mTimeBroadcastReceiver;
    private boolean isRunning = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        initViews(view);
        mSongs = getArguments().getParcelableArrayList(KEY_SONGS);
        mPosition = getArguments().getInt(KEY_POSITION);
        if (!isRunning) {
            mIntent = new Intent(getActivity(), MusicService.class);
            mIntent.setAction(Action.INTENT.getValue());
            mIntent.putExtra(KEY_URL, mSongs.get(mPosition).getSource());
            getActivity().startService(mIntent);
            isRunning = true;
        } else {
            Intent intent = new Intent();
            intent.putExtra(KEY_START, Action.START.getValue());
            intent.putExtra(KEY_URL, mSongs.get(mPosition).getSource());
            getActivity().sendBroadcast(intent);
        }
        setTimeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(Action.SEEK.getValue());
        getActivity().registerReceiver(mTimeBroadcastReceiver, intentFilter);
        return view;
    }

    private void setTimeBroadcastReceiver() {
        mTimeBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateTime(intent);
            }
        };
    }

    private void updateTime(Intent intent) {
        if (mLength == 0) {
            mLength = intent.getIntExtra(MusicService.KEY_TIME, 0);
            mSeekBar.setMax(mLength);
            return;
        }
        mSeekBar.setProgress(intent.getIntExtra(MusicService.KEY_CURRENT_TIME, 0));
    }

    private void initViews(View view) {
        mTvCurrentTime = (TextView) view.findViewById(R.id.tvCurrentTime);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        mImgShuffle = (ImageView) view.findViewById(R.id.imgShuffle);
        mImgPrevious = (ImageView) view.findViewById(R.id.imgPrevious);
        mImgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        mImgNext = (ImageView) view.findViewById(R.id.imgNext);
        mImgLoop = (ImageView) view.findViewById(R.id.imgLoop);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
    }

    public static PlayMusicFragment newInstance(ArrayList<Song> songs, int position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_SONGS, songs);
        args.putInt(KEY_POSITION, position);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mTimeBroadcastReceiver);
    }
}
