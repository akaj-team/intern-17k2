package vn.asiantech.internship.music;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Used to play music in background.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MusicService extends Service {
    private NotificationBroadCast mNotificationBroadCast = new NotificationBroadCast();
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs = new ArrayList<>();
    private CountDownTimer mCountDownTimer;
    private int mCurrentSongIndex = 0;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mNotificationBroadCast, screenStateFilter);
        switch (intent.getAction()) {
            case "play":
                mSongs = intent.getParcelableArrayListExtra("songs");
                checkState();
                if (mMediaPlayer == null) {
                    playSong(mCurrentSongIndex);
                } else {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        final Intent timeIntent = new Intent("pause");
                        timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                        sendBroadcast(timeIntent);
                        mCountDownTimer.cancel();
                    } else {
                        mMediaPlayer.start();
                        mCountDownTimer.start();
                        final Intent playNextIntent = new Intent("playnext");
                        playNextIntent.putExtra("next", mMediaPlayer.getCurrentPosition() + "");
                        sendBroadcast(playNextIntent);
                    }
                }
                break;
            case "seekto":
                int time = intent.getIntExtra("chooseTime", 0);
                mMediaPlayer.seekTo(time);
                mMediaPlayer.start();
                break;
            case "next":
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                if (mCurrentSongIndex < (mSongs.size() - 1)) {
                    playSong(mCurrentSongIndex + 1);
                    mCurrentSongIndex = mCurrentSongIndex + 1;
                } else {
                    playSong(0);
                    mCurrentSongIndex = 0;
                }
                break;
            case "previous":
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                if (mCurrentSongIndex > 0) {
                    playSong(mCurrentSongIndex - 1);
                    mCurrentSongIndex = mCurrentSongIndex - 1;
                } else {
                    playSong(mSongs.size() - 1);
                    mCurrentSongIndex = mSongs.size() - 1;
                }
                break;
            case "shuffle":
                if (mIsShuffle) {
                    mIsShuffle = false;
                    Toast.makeText(getApplicationContext(), "Tắt phát nhạc random", Toast.LENGTH_SHORT).show();
                } else {
                    mIsShuffle = true;
                    Toast.makeText(getApplicationContext(), "Bật phát nhạc random", Toast.LENGTH_SHORT).show();
                    mIsAutoNext = false;
                }
                break;
            case "autoNext":
                if (mIsAutoNext) {
                    mIsAutoNext = false;
                    Toast.makeText(getApplicationContext(), "Tắt lặp lại", Toast.LENGTH_SHORT).show();
                } else {
                    mIsAutoNext = true;
                    Toast.makeText(getApplicationContext(), "Bật lặp lại", Toast.LENGTH_SHORT).show();
                    mIsShuffle = false;
                }
                break;
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotificationBroadCast);
    }

    private void playSong(int position) {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mSongs.get(position).getUrl());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer.start();
            }
        });
        final Intent timeIntent = new Intent("seek");
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("time", mMediaPlayer.getDuration() + "");
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    private void checkState() {
        if (mIsAutoNext) {
            playSong(mCurrentSongIndex);
        } else if (mIsShuffle) {
            Random rand = new Random();
            mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
            playSong(mCurrentSongIndex);
        } else {
            if (mCurrentSongIndex < (mSongs.size() - 1)) {
                playSong(mCurrentSongIndex);
                mCurrentSongIndex = mCurrentSongIndex + 1;
            } else if (mCurrentSongIndex == (mSongs.size())) {
                playSong(0);
                mCurrentSongIndex = 0;
            }
        }
    }
}
