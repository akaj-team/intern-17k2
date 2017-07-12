package vn.asiantech.internship.ui.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Music;

/**
 * MusicPlayFragment
 */
public class MusicPlayFragment extends Fragment implements View.OnClickListener {
    private static final String KEY = "KEY";
    private SeekBar mSeekBar;
    private ImageView mImgPause;
    private ImageView mImgShuffle;
    private ImageView mImgRepeat;
    private TextView mTvCurrentTime;
    private TextView mTvDurationTime;

    private boolean mIsShuffle;
    private boolean mIsRepeat;
    private int mLength;
    private int mPlay;
    private int mPositionPlay;
    private MusicBroadcastReceiver mMusicBroadcastReceiver;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            processTime(intent);
        }
    };

    public static MusicPlayFragment newInstance(int position) {
        MusicPlayFragment musicPlayFragment = new MusicPlayFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, position);
        musicPlayFragment.setArguments(bundle);
        return musicPlayFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPositionPlay = getArguments().getInt(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_music_play, container, false);

        mImgPause = (ImageView) v.findViewById(R.id.imgPause);
        mImgShuffle = (ImageView) v.findViewById(R.id.imgShuffle);
        mImgRepeat = (ImageView) v.findViewById(R.id.imgRepeat);
        mSeekBar = (SeekBar) v.findViewById(R.id.seekBar);
        mTvCurrentTime = (TextView) v.findViewById(R.id.tvCurrentTime);
        mTvDurationTime = (TextView) v.findViewById(R.id.tvDurationTime);
        mMusicBroadcastReceiver = new MusicBroadcastReceiver();
        initIntentFilter();

        // Init Listener
        v.findViewById(R.id.imgPause).setOnClickListener(this);
        v.findViewById(R.id.imgNext).setOnClickListener(this);
        v.findViewById(R.id.imgPrevious).setOnClickListener(this);
        v.findViewById(R.id.imgShuffle).setOnClickListener(this);
        v.findViewById(R.id.imgRepeat).setOnClickListener(this);

        return v;
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        getActivity().registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent playIntent = new Intent(getActivity(), NotificationServiceMusic.class);
                playIntent.putExtra("chooseTime", seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                getActivity().startService(playIntent);
            }
        });
    }

    // Change and play song in @position
    public void changePosition(int position) {
        mPositionPlay = position;
        mPlay = 1;
        mImgPause.setImageResource(R.drawable.vector_pause);

        Intent intent = new Intent(getActivity(), NotificationServiceMusic.class);
        if (getActivity() instanceof MusicActivity) {
            ArrayList<Music> songs;
            songs = ((MusicActivity) getActivity()).getSongs();
            intent.putParcelableArrayListExtra(Action.INTENT.getValue(), songs);
            intent.putExtra(Action.POSITION.getValue(), mPositionPlay);
            intent.setAction(Action.START.getValue());
            getActivity().startService(intent);
        }
    }

    private void processTime(Intent intent) {
        int position = intent.getIntExtra("second", 0);
        boolean stop = intent.getBooleanExtra("stop", false);
        if (mLength == 0) {
            mLength = intent.getIntExtra("time", 0);
            mSeekBar.setMax(mLength);
            mSeekBar.setProgress(0);
            mTvDurationTime.setText(Action.TIME.getTime(mLength));
            mTvCurrentTime.setText(Action.TIME.getTime(position));
            return;
        }
        if (stop) {
            if (!mIsRepeat) {
                mImgPause.setImageResource(R.drawable.vector_play);
                mPlay = 0;
            }
            mLength = 0;
            mSeekBar.setProgress(0);
            mTvDurationTime.setText(Action.TIME.getTime(mLength));
            mTvCurrentTime.setText(Action.TIME.getTime(mLength));
            return;
        }

        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(Action.TIME.getTime(position));
        Intent progressBarIntent = new Intent(getActivity(), NotificationServiceMusic.class);
        progressBarIntent.putExtra("position", position);
        progressBarIntent.setAction(Action.PROGRESSBAR.getValue());
        getActivity().startService(progressBarIntent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), NotificationServiceMusic.class);
        switch (view.getId()) {
            case R.id.imgPause:
                if (mPlay == 0) {
                    mPlay = 1;
                    mImgPause.setImageResource(R.drawable.vector_pause);
                    if (getActivity() instanceof MusicActivity) {
                        ArrayList<Music> songs;
                        songs = ((MusicActivity) getActivity()).getSongs();
                        intent.putParcelableArrayListExtra(Action.INTENT.getValue(), songs);
                        intent.putExtra(Action.POSITION.getValue(), mPositionPlay);
                        intent.setAction(Action.START.getValue());
                    }
                } else if (mPlay == 1) {
                    mPlay = 2;
                    mImgPause.setImageResource(R.drawable.vector_play);
                    intent.setAction(Action.PAUSE.getValue());
                } else {
                    mPlay = 1;
                    mImgPause.setImageResource(R.drawable.vector_pause);
                    intent.setAction(Action.PAUSE.getValue());
                }
                getActivity().startService(intent);
                break;
            case R.id.imgNext:
                if (mPlay != 0) {
                    int size = ((MusicActivity) getActivity()).getSongs().size();
                    intent.setAction(Action.NEXT.getValue());
                    getActivity().startService(intent);
                    mPositionPlay++;
                    mLength = 0;
                    if (mPositionPlay == size) {
                        mPositionPlay = 0;
                    }
                    sendAndSetHighLight(mPositionPlay);
                } else {
                    showTip();
                }
                break;
            case R.id.imgPrevious:
                if (mPlay != 0) {
                    int size = ((MusicActivity) getActivity()).getSongs().size();
                    intent.setAction(Action.PREVIOUS.getValue());
                    getActivity().startService(intent);
                    mPositionPlay--;
                    mLength = 0;
                    if (mPositionPlay < 0) {
                        mPositionPlay = size - 1;
                    }
                    sendAndSetHighLight(mPositionPlay);
                } else {
                    showTip();
                }
                break;
            case R.id.imgShuffle:
                mIsShuffle = !mIsShuffle;
                mImgShuffle.setSelected(mIsShuffle);
                intent.setAction(Action.SHUFFLE.getValue());
                getActivity().startService(intent);
                break;
            case R.id.imgRepeat:
                mIsRepeat = !mIsRepeat;
                mImgRepeat.setSelected(mIsRepeat);
                intent.setAction(Action.REPEAT.getValue());
                getActivity().startService(intent);
                break;
        }
    }

    private void sendAndSetHighLight(int position) {
        if (getActivity() instanceof MusicActivity) {
            ((MusicActivity) getActivity()).setHighLight(position);
        }
    }

    private void showTip() {
        Toast.makeText(getActivity(), getString(R.string.error_message_you_must_play_first), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(mMusicBroadcastReceiver, screenStateFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
        getActivity().unregisterReceiver(mMusicBroadcastReceiver);
    }
}
