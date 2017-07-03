package vn.asiantech.internship.day19.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day19.adapter.SongAdapter;
import vn.asiantech.internship.day19.model.Song;
import vn.asiantech.internship.day19.model.Utils;
import vn.asiantech.internship.day19.service.SongService;
import vn.asiantech.internship.day7.ex2.ui.MainActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 01/07/2017.
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TYPE_SONGS = "Songs";
    public static final String TYPE_INDEX = "Position";
    public static final String TYPE_AUTO_NEXT = "Auto Next";

    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;

    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnPause;
    private SeekBar mSeekBar;
    private TextView mTvTimeTotal;
    private TextView mTvCurrentTime;
    private TextView mTvSongNow;
    private TextView mTvArtistNow;

    private Handler handler = new Handler();
    private List<Song> mSongs = new ArrayList<>();
    private Utils mUtils = new Utils();
    private int mCurrentPlay;
    private boolean mCheckShuffle;
    private SongAdapter mSongAdapter;
    private MyBroadcast mMyBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMusic);
        mSeekBar = (SeekBar) findViewById(R.id.seekBarMusic);
        mTvCurrentTime = (TextView) findViewById(R.id.tvTimeNow);
        mTvTimeTotal = (TextView) findViewById(R.id.tvTimeTotal);
        mTvSongNow = (TextView) findViewById(R.id.tvNowPlaySong);
        mTvArtistNow = (TextView) findViewById(R.id.tvNowPlayArtist);

        mImgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        mImgBtnPause = (ImageButton) findViewById(R.id.imgBtnPause);
        ImageButton imgBtnPrevious = (ImageButton) findViewById(R.id.imgBtnPrevious);
        ImageButton imgBtnNext = (ImageButton) findViewById(R.id.imgBtnNext);
        ImageButton imgBtnShuffle = (ImageButton) findViewById(R.id.imgBtnShuffle);
        ImageButton imgBtnShuffleSelected = (ImageButton) findViewById(R.id.imgBtnShuffleSelected);
        ImageButton imgBtnAutoNext = (ImageButton) findViewById(R.id.imgBtnAutoNext);
        ImageButton imgBtnAutoNextSelected = (ImageButton) findViewById(R.id.imgBtnAutoNextSelected);

        mImgBtnPlay.setOnClickListener(this);
        mImgBtnPause.setOnClickListener(this);
        imgBtnAutoNext.setOnClickListener(this);
        imgBtnNext.setOnClickListener(this);
        imgBtnPrevious.setOnClickListener(this);
        imgBtnShuffleSelected.setOnClickListener(this);
        imgBtnShuffle.setOnClickListener(this);
        imgBtnAutoNextSelected.setOnClickListener(this);

        String[] songUrls = getApplicationContext().getResources().getStringArray(R.array.song_urls);
        String[] songNames = getApplicationContext().getResources().getStringArray(R.array.song_names);
        String[] songArtists = getApplicationContext().getResources().getStringArray(R.array.song_artists);

        for (int i = 0; i < songUrls.length; i++) {
            mSongs.add(new Song(songNames[i], songArtists[i], 0, songUrls[i], 0));
        }
        mSongAdapter = new SongAdapter(mSongs, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mSongAdapter);
        mSongAdapter.notifyDataSetChanged();

        sendListSong();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // No-op
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            // No-op
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (SongService.sMediaPlayer != null) {
                    SongService.sMediaPlayer.seekTo(mUtils.progressToTimer(seekBar.getProgress(), SongService.sMediaPlayer.getDuration()));
                } else {
                    seekBar.setProgress(0);
                }
            }
        });
        update();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMyBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SongService.ACTION_SEND_POSITION);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mMyBroadcast, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcast);
        Intent myIntent = new Intent(this, SongService.class);
        stopService(myIntent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgBtnPlay:
                mImgBtnPlay.setVisibility(View.INVISIBLE);
                mImgBtnPause.setVisibility(View.VISIBLE);
                sendIntent(SongService.ACTION_PLAY);
                break;
            case R.id.imgBtnPause:
                mImgBtnPlay.setVisibility(View.VISIBLE);
                mImgBtnPause.setVisibility(View.INVISIBLE);
                sendIntent(SongService.ACTION_PAUSE);
                break;
            case R.id.imgBtnNext:
                if (mCheckShuffle) {
                    mCurrentPlay = getRandomPosition();
                } else {
                    mCurrentPlay++;
                    if (mCurrentPlay == mSongs.size()) {
                        mCurrentPlay = 0;
                    }
                }
                sendIntent(SongService.ACTION_NEXT);
                mSongAdapter.setPosition(mCurrentPlay);
                break;
            case R.id.imgBtnShuffle:
                mCheckShuffle = true;
                break;
            case R.id.imgBtnShuffleSelected:
                mCheckShuffle = false;
                break;
            case R.id.imgBtnPrevious:
                if (mCheckShuffle) {
                    mCurrentPlay = getRandomPosition();
                } else {
                    mCurrentPlay--;
                    if (mCurrentPlay < 0) {
                        mCurrentPlay = mSongs.size() - 1;
                    }
                }
                sendIntent(SongService.ACTION_PREVIOUS);
                mSongAdapter.setPosition(mCurrentPlay);
                break;
            case R.id.imgBtnAutoNext:
                Intent intent = new Intent();
                intent.setAction(SongService.ACTION_AUTO_NEXT);
                intent.putExtra(TYPE_AUTO_NEXT, true);
                startService(intent);
            default:
                Intent intent1 = new Intent();
                intent1.setAction(SongService.ACTION_AUTO_NEXT);
                intent1.putExtra(TYPE_AUTO_NEXT, false);
                startService(intent1);
        }
    }

    private void sendIntent(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(TYPE_INDEX, mCurrentPlay);
        startService(intent);
    }

    private void sendListSong() {
        Intent intent = new Intent();
        intent.setAction(SongService.ACTION_SONGS);
        intent.putParcelableArrayListExtra(TYPE_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        startService(intent);
    }

    public ImageButton getImgBtnPlay() {
        return mImgBtnPlay;
    }

    private int getRandomPosition() {
        return new Random().nextInt(mSongs.size());
    }

    private void initNotification() {
        final RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification_music);
        views.setImageViewResource(R.id.imgSongNotification, R.mipmap.ic_photo);
        views.setTextViewText(R.id.tvSongNotification, mSongs.get(mCurrentPlay).getSongName());
        views.setTextViewText(R.id.tvArtistNotification, mSongs.get(mCurrentPlay).getSongArtist());
        views.setImageViewResource(R.id.imgBtnCloseNotification, R.mipmap.ic_cancel);
        views.setProgressBar(R.id.progressBarNotification,100,0,false);

        final Handler handlerNotification = new Handler();

        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (SongService.sMediaPlayer != null) {
                    long current = SongService.sMediaPlayer.getCurrentPosition();
                    long total = SongService.sMediaPlayer.getDuration();
                    views.setTextViewText(R.id.tvTimeNowNotification, mUtils.showTime(current));
                    views.setTextViewText(R.id.tvTimeTotalNotification, mUtils.showTime(total));
                    views.setProgressBar(R.id.progressBarNotification, 100, mUtils.getProgressPercentage(current, total), false);
                }
                handlerNotification.post(this);
            }
        };
        handlerNotification.post(update);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_avatar);
        builder.setContent(views);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, builder.build());
    }

    private void update() {
        handler.post(update);
    }

    Runnable update = new Runnable() {
        @Override
        public void run() {
            if (SongService.sMediaPlayer != null) {
                long current = SongService.sMediaPlayer.getCurrentPosition();
                long total = SongService.sMediaPlayer.getDuration();
                mTvCurrentTime.setText(mUtils.showTime(current));
                mTvTimeTotal.setText(mUtils.showTime(total));
                mTvSongNow.setText(mSongs.get(mCurrentPlay).getSongName());
                mTvArtistNow.setText(mSongs.get(mCurrentPlay).getSongArtist());
                mSeekBar.setProgress(mUtils.getProgressPercentage(current, total));
            }
            handler.post(this);
        }
    };

    // Create MyBroadcast
    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SongService.ACTION_SEND_POSITION)) {
                mCurrentPlay = intent.getIntExtra(SongService.TYPE_POSITION, -1);
                mSongAdapter.setPosition(mCurrentPlay);
            } else if (s.equals(Intent.ACTION_SCREEN_OFF)) {
                Log.d("kkkkk", "onReceive: ");
                initNotification();
            }
        }
    }
}
