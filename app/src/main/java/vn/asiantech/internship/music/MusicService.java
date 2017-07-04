package vn.asiantech.internship.music;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs = new ArrayList<>();
    private CountDownTimer mCountDownTimer;
    private int mCurrentSongIndex = 0;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;
    private NotificationBroadCast mNotificationBroadCast = new NotificationBroadCast();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case "PLAY":
                    IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                    registerReceiver(mNotificationBroadCast, screenStateFilter);
                    mSongs = intent.getParcelableArrayListExtra("songs");
                    if (mMediaPlayer == null) {
                        playSong(mCurrentSongIndex);
                        final Intent timeIntent = new Intent(Action.START.getValue());
                        sendBroadcast(timeIntent);
                    } else {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                            final Intent timeIntent = new Intent(Action.PAUSE.getValue());
                            timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                            sendBroadcast(timeIntent);
                            mCountDownTimer.cancel();
                        } else {
                            mMediaPlayer.start();
                            mCountDownTimer.start();
                            final Intent playNextIntent = new Intent(Action.PLAYNEXT.getValue());
                            playNextIntent.putExtra("next", mMediaPlayer.getCurrentPosition() + "");
                            sendBroadcast(playNextIntent);
                        }
                    }
                    break;
                case "SEEKTO":
                    int time = intent.getIntExtra("chooseTime", 0);
                    mMediaPlayer.seekTo(time);
                    mMediaPlayer.start();
                    break;
                case "NEXT":
                    if (mMediaPlayer != null) {
                        final Intent playNextIntent = new Intent(Action.NEXT.getValue());
                        sendBroadcast(playNextIntent);
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                        }
                        if (mIsShuffle) {
                            Random rand = new Random();
                            mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
                            playSong(mCurrentSongIndex);
                        } else {
                            if (mCurrentSongIndex < (mSongs.size() - 1)) {
                                playSong(mCurrentSongIndex + 1);
                                mCurrentSongIndex = mCurrentSongIndex + 1;
                            } else {
                                playSong(0);
                                mCurrentSongIndex = 0;
                            }
                        }
                    }
                    break;
                case "PREVIOUS":
                    if (mMediaPlayer != null) {
                        final Intent playpreviousIntent = new Intent(Action.PREVIOUS.getValue());
                        sendBroadcast(playpreviousIntent);
                        if (mMediaPlayer.isPlaying() && mMediaPlayer != null) {
                            mMediaPlayer.pause();
                        }
                        if (mIsShuffle) {
                            Random rand = new Random();
                            mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
                            playSong(mCurrentSongIndex);
                        } else {
                            if (mCurrentSongIndex > 0) {
                                playSong(mCurrentSongIndex - 1);
                                mCurrentSongIndex = mCurrentSongIndex - 1;
                            } else {
                                playSong(mSongs.size() - 1);
                                mCurrentSongIndex = mSongs.size() - 1;
                            }
                        }
                    }
                    break;
                case "SHUFFEL":
                    if (mIsShuffle) {
                        mIsShuffle = false;
                        final Intent timeIntent = new Intent(Action.NOTSHUFFEL.getValue());
                        sendBroadcast(timeIntent);
                    } else {
                        mIsShuffle = true;
                        final Intent timeIntent = new Intent(Action.SHUFFEL.getValue());
                        sendBroadcast(timeIntent);
                    }
                    break;
                case "AUTONEXT":
                    if (mIsAutoNext) {
                        mIsAutoNext = false;
                        final Intent timeIntent = new Intent(Action.NOTAUTONEXT.getValue());
                        sendBroadcast(timeIntent);
                    } else {
                        mIsAutoNext = true;
                        final Intent timeIntent = new Intent(Action.AUTONEXT.getValue());
                        sendBroadcast(timeIntent);
                    }
                    break;
                case "PLAYNEXT":
                    if (mIsAutoNext) {
                        playSong(mCurrentSongIndex);
                    } else if (mIsShuffle) {
                        Random rand = new Random();
                        mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
                        playSong(mCurrentSongIndex);
                    } else {
                        if (mCurrentSongIndex < (mSongs.size() - 1)) {
                            playSong(mCurrentSongIndex + 1);
                            mCurrentSongIndex = mCurrentSongIndex + 1;
                        } else if (mCurrentSongIndex == (mSongs.size())) {
                            playSong(0);
                            mCurrentSongIndex = 0;
                        }
                    }
                    break;
                case "chooseSong":
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    }
                    mCurrentSongIndex = intent.getIntExtra("currentSong", 0);
                    playSong(mCurrentSongIndex);
                    break;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void playSong(int position) {
        final Intent updateSongNameIntent = new Intent(Action.UPDATE.getValue());
        updateSongNameIntent.putExtra("songName", mSongs.get(mCurrentSongIndex).getName());
        sendBroadcast(updateSongNameIntent);
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
        final Intent timeIntent = new Intent(Action.SEEK.getValue());
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("time", mMediaPlayer.getDuration() + "");
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                Bundle bundle = new Bundle();
                bundle.putParcelable("song", mSongs.get(mCurrentSongIndex));
                timeIntent.putExtras(bundle);
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }
}
