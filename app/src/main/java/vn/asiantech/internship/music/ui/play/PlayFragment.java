package vn.asiantech.internship.music.ui.play;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.music.models.Action;
import vn.asiantech.internship.music.models.Song;
import vn.asiantech.internship.music.services.MusicService;
import vn.asiantech.internship.music.ui.home.SongActivity;
import vn.asiantech.internship.music.utils.TimeUtil;

public class PlayFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = PlayFragment.class.getSimpleName();
    private TextView mTvCurrentTime;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private ImageView mImgShuffle;
    private ImageView mImgPrevious;
    private ImageView mImgPlay;
    private ImageView mImgNext;
    private ImageView mImgRepeat;
    private Toolbar mToolbar;
    private int mImgPlayStatus;
    private int mImgShuffleStatus;
    private int mImgRepeatStatus;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private int mLength;
    private int mPosition;
    private boolean mDoStart;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(Action.SEEK.getValue())) {
                    processTime(intent);
                }
                if (intent.getAction().equals(Action.SEND_INFO.getValue())) {
                    Song song = intent.getParcelableExtra(SongActivity.KEY_SONG);
                    mToolbar.setTitle(song.getTitle() + " --- " + song.getArtist());
                }
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            mPosition = getArguments().getInt(SongActivity.KEY_POSITION);
            mDoStart = getArguments().getBoolean("do_start");
        }
        initSharedPreferences();
    }

    public static PlayFragment newInstance(int position, boolean start) {
        Bundle args = new Bundle();
        args.putInt(SongActivity.KEY_POSITION, position);
        args.putBoolean("do_start", start);
        PlayFragment fragment = new PlayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        initToolBar(view);
        initViews(view);
        initState();
        setClickButton();
        initIntentFilter();
        if (mDoStart) {
            Intent startIntent = new Intent(getActivity(), MusicService.class);
            startIntent.setAction(Action.START.getValue());
            startIntent.putExtra(SongActivity.KEY_POSITION, mPosition);
            getActivity().startService(startIntent);
        }
        return view;
    }

    private void initToolBar(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.wait));
        mToolbar.setTitleTextColor(getActivity().getResources().getColor(android.R.color.holo_red_light));
        mToolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_red_700_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initIntentFilter() {
        IntentFilter filter = new IntentFilter(Action.SEEK.getValue());
        filter.addAction(Action.SEND_INFO.getValue());
        getActivity().registerReceiver(mBroadcastReceiver, filter);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTvCurrentTime.setText(TimeUtil.getTime(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mTvCurrentTime.setText(TimeUtil.getTime(seekBar.getProgress()));
                Intent playIntent = new Intent(getActivity(), MusicService.class);
                playIntent.putExtra(SongActivity.KEY_CHOOSE_TIME, seekBar.getProgress());
                playIntent.setAction(Action.SEEK_TO.getValue());
                Log.d(TAG, "onStopTrackingTouch: seek to " + seekBar.getProgress());
                getActivity().startService(playIntent);
            }
        });
    }

    private void initSharedPreferences() {
        mSharedPreferences = getActivity().getSharedPreferences("status", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        if (!mSharedPreferences.contains(SongActivity.KEY_SHUFFLE_STATUS)) {
            mEditor.putInt(SongActivity.KEY_SHUFFLE_STATUS, SongActivity.NO_SHUFFLE);
            mEditor.putInt(SongActivity.KEY_REPEAT_STATUS, SongActivity.NO_REPEAT);
            mEditor.apply();
            mEditor.commit();
        }
    }

    private void initViews(View view) {
        mTvCurrentTime = (TextView) view.findViewById(R.id.tvCurrentTime);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        mImgShuffle = (ImageView) view.findViewById(R.id.imgShuffle);
        mImgPrevious = (ImageView) view.findViewById(R.id.imgPrevious);
        mImgPlay = (ImageView) view.findViewById(R.id.imgPlay);
        mImgNext = (ImageView) view.findViewById(R.id.imgNext);
        mImgRepeat = (ImageView) view.findViewById(R.id.imgRepeat);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
    }

    private void initState() {
        mImgPlayStatus = mSharedPreferences.getInt(SongActivity.KEY_PLAY_STATUS, SongActivity.PLAY_STATUS);
        switch (mImgPlayStatus) {
            case SongActivity.PAUSE_STATUS:
                mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                break;
            case SongActivity.PLAY_STATUS:
                mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
        }
        mImgShuffleStatus = mSharedPreferences.getInt(SongActivity.KEY_SHUFFLE_STATUS, SongActivity.NO_SHUFFLE);
        switch (mImgShuffleStatus) {
            case SongActivity.NO_SHUFFLE:
                mImgShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
                break;
            case SongActivity.SHUFFLE:
                mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_24dp);
                break;
        }
        mImgRepeatStatus = mSharedPreferences.getInt(SongActivity.KEY_REPEAT_STATUS, SongActivity.NO_REPEAT);
        switch (mImgRepeatStatus) {
            case SongActivity.NO_REPEAT:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                break;
            case SongActivity.REPEAT:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_red_700_24dp);
                break;
            case SongActivity.REPEAT_ONE:
                mImgRepeat.setImageResource(R.drawable.ic_repeat_one_red_700_24dp);
                break;
        }
    }

    private void setClickButton() {
        mImgRepeat.setOnClickListener(this);
        mImgShuffle.setOnClickListener(this);
        mImgPrevious.setOnClickListener(this);
        mImgPlay.setOnClickListener(this);
        mImgNext.setOnClickListener(this);
        mImgRepeat.setOnClickListener(this);
    }

    private void processTime(Intent intent) {
        mLength = intent.getIntExtra(MusicService.KEY_TIME, 0);
        mSeekBar.setMax(mLength);
        mTvTime.setText(TimeUtil.getTime(mLength));
        mToolbar.setTitle(intent.getStringExtra(MusicService.KEY_TITLE));
        int position = intent.getIntExtra(MusicService.KEY_CURRENT_TIME, 0);
        Log.d(TAG, "processTime: " + position);
        mSeekBar.setProgress(position);
        mTvCurrentTime.setText(TimeUtil.getTime(position));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPlay:
                if (mImgPlayStatus == SongActivity.PLAY_STATUS) {
                    mImgPlayStatus = SongActivity.PAUSE_STATUS;
                    mEditor.putInt(SongActivity.KEY_PLAY_STATUS, mImgPlayStatus);
                    mImgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    sendPlayStatus();

                    Intent pauseIntent = new Intent(getActivity(), MusicService.class);
                    pauseIntent.setAction(Action.PAUSE.getValue());
                    getActivity().startService(pauseIntent);
                    break;
                }
                if (mImgPlayStatus == SongActivity.PAUSE_STATUS) {
                    mImgPlayStatus = SongActivity.PLAY_STATUS;
                    mEditor.putInt(SongActivity.KEY_PLAY_STATUS, mImgPlayStatus);
                    mImgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    sendPlayStatus();

                    Intent resumeIntent = new Intent(getActivity(), MusicService.class);
                    resumeIntent.setAction(Action.RESUME.getValue());
                    getActivity().startService(resumeIntent);
                    break;
                }
            case R.id.imgShuffle:
                if (mImgShuffleStatus == SongActivity.NO_SHUFFLE) {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_red_700_24dp);
                    mImgShuffleStatus = SongActivity.SHUFFLE;
                    mEditor.putInt(SongActivity.KEY_SHUFFLE_STATUS, mImgShuffleStatus);
                } else {
                    mImgShuffle.setImageResource(R.drawable.ic_shuffle_black_24dp);
                    mImgShuffleStatus = SongActivity.NO_SHUFFLE;
                    mEditor.putInt(SongActivity.KEY_SHUFFLE_STATUS, mImgShuffleStatus);
                }
                sendShuffleStatus();
                break;
            case R.id.imgPrevious:
                sendPrevious();
                break;
            case R.id.imgNext:
                sendNext();
                break;
            case R.id.imgRepeat:
                if (mImgRepeatStatus == SongActivity.NO_REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_red_700_24dp);
                    mImgRepeatStatus = SongActivity.REPEAT;
                    mEditor.putInt(SongActivity.KEY_REPEAT_STATUS, mImgRepeatStatus);
                    sendRepeatStatus();
                    break;
                }
                if (mImgRepeatStatus == SongActivity.REPEAT) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_one_red_700_24dp);
                    mImgRepeatStatus = SongActivity.REPEAT_ONE;
                    mEditor.putInt(SongActivity.KEY_REPEAT_STATUS, mImgRepeatStatus);
                    sendRepeatStatus();
                    break;
                }
                if (mImgRepeatStatus == SongActivity.REPEAT_ONE) {
                    mImgRepeat.setImageResource(R.drawable.ic_repeat_black_24dp);
                    mImgRepeatStatus = SongActivity.NO_REPEAT;
                    mEditor.putInt(SongActivity.KEY_REPEAT_STATUS, mImgRepeatStatus);
                    sendRepeatStatus();
                    break;
                }
        }
        mEditor.commit();
    }

    private void sendNext() {
        Intent nextIntent = new Intent(Action.NEXT.getValue());
        getActivity().sendBroadcast(nextIntent);
    }

    private void sendPrevious() {
        Intent previousIntent = new Intent(Action.PREVIOUS.getValue());
        getActivity().sendBroadcast(previousIntent);
    }

    private void sendPlayStatus() {
        Intent intentPlay = new Intent(Action.PLAY.getValue());
        intentPlay.putExtra(SongActivity.KEY_PLAY_STATUS, mImgPlayStatus);
        getActivity().sendBroadcast(intentPlay);
    }

    private void sendShuffleStatus() {
        Intent intentShuffle = new Intent(Action.SHUFFLE.getValue());
        intentShuffle.putExtra(SongActivity.KEY_SHUFFLE_STATUS, mImgShuffleStatus);
        getActivity().sendBroadcast(intentShuffle);
    }

    private void sendRepeatStatus() {
        Intent intentRepeat = new Intent(Action.REPEAT.getValue());
        intentRepeat.putExtra(SongActivity.KEY_REPEAT_STATUS, mImgRepeatStatus);
        getActivity().sendBroadcast(intentRepeat);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
        Intent changeSPIntent = new Intent();
        changeSPIntent.setAction(Action.CHANGE.getValue());
        getActivity().sendBroadcast(changeSPIntent);
    }

    @Override
    public void onPause() {
        Intent showNotificationIntent = new Intent(Action.SHOW.getValue());
        getActivity().sendBroadcast(showNotificationIntent);
        super.onPause();
    }
}
