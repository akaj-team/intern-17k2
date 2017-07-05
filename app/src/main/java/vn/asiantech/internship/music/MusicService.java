package vn.asiantech.internship.music;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

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
    private static final String TAG = MusicService.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs = new ArrayList<>();
    private CountDownTimer mCountDownTimer;
    private int mCurrentSongIndex = 0;
    private boolean mIsShuffle;
    private boolean mIsAutoNext;
    private boolean mIsPause;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Action.SEEKTO.getValue())) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.seekTo(intent.getIntExtra("chooseTime", 0));
                }
            }
        }
    };
    private final NotificationBroadCast mNotificationBroadCast = new NotificationBroadCast();

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mNotificationBroadCast, screenStateFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter mStartFilter = new IntentFilter(Action.SEEKTO.getValue());
        registerReceiver(mReceiver, mStartFilter);
        if (intent.getAction() != null) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Action.PLAY.getValue())) {
                mSongs = intent.getParcelableArrayListExtra("songs");
                play();
            } else if (TextUtils.equals(action, Action.NEXT.getValue())) {
                next();
            } else if (TextUtils.equals(action, Action.PREVIOUS.getValue())) {
                previous();
            } else if (TextUtils.equals(action, Action.SHUFFEL.getValue())) {
                shuffle();
            } else if (TextUtils.equals(action, Action.REPLAY.getValue())) {
                replay();
            } else if (TextUtils.equals(action, Action.AUTONEXT.getValue())) {
                autoNext();
            } else if (TextUtils.equals(action, Action.CHOOSESONGFROMLIST.getValue())) {
                mSongs = intent.getParcelableArrayListExtra("songs");
                mCurrentSongIndex = intent.getIntExtra("currentSong", 0);
                playSongWithPosition(mCurrentSongIndex);
            } else if (TextUtils.equals(action, Action.CANCEL.getValue())) {
                stopSelf();
                stopForeground(true);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancel(1);
                final Intent cancelIntent = new Intent(Action.CANCEL.getValue());
                sendBroadcast(cancelIntent);
            }
        }
        handleCalling();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void playSongWithPosition(int position) {
        final Intent updateSongNameIntent = new Intent(Action.UPDATE.getValue());
        updateSongNameIntent.putExtra("songName", mSongs.get(mCurrentSongIndex).getName());
        sendBroadcast(updateSongNameIntent);
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                final Intent isPlayingIntent = new Intent(Action.ISPLAYING.getValue());
                sendBroadcast(isPlayingIntent);
            } else if (mMediaPlayer.isPlaying()) {
                final Intent isPauseIntent = new Intent(Action.ISPAUSE.getValue());
                sendBroadcast(isPauseIntent);
            }
        }
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mSongs.get(position).getUrl());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
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

    private void play() {
        if (mMediaPlayer == null) {
            playSongWithPosition(mCurrentSongIndex);
            final Intent timeIntent = new Intent(Action.START.getValue());
            sendBroadcast(timeIntent);
        } else {
            if (mMediaPlayer.isPlaying()) {
                mIsPause = false;
                final Intent timeIntent = new Intent(Action.PAUSE.getValue());
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition() + "");
                sendBroadcast(timeIntent);
                mMediaPlayer.pause();
                mCountDownTimer.cancel();
            } else {
                mIsPause = true;
                mMediaPlayer.start();
                mCountDownTimer.start();
                final Intent playNextIntent = new Intent(Action.AUTONEXT.getValue());
                playNextIntent.putExtra("next", mMediaPlayer.getCurrentPosition() + "");
                sendBroadcast(playNextIntent);
            }
        }
    }

    private void next() {
        if (mMediaPlayer != null && mSongs.size() != 0) {
            if (mIsShuffle) {
                random();
            } else {
                if (mCurrentSongIndex < (mSongs.size() - 1)) {
                    playSongWithPosition(mCurrentSongIndex + 1);
                    mCurrentSongIndex = mCurrentSongIndex + 1;
                } else {
                    playSongWithPosition(0);
                    mCurrentSongIndex = 0;
                }
            }
        }
    }

    private void previous() {
        if (mMediaPlayer != null && mSongs.size() != 0) {
            if (mIsShuffle) {
                random();
            } else {
                if (mCurrentSongIndex > 0) {
                    playSongWithPosition(mCurrentSongIndex - 1);
                    mCurrentSongIndex = mCurrentSongIndex - 1;
                } else {
                    playSongWithPosition(mSongs.size() - 1);
                    mCurrentSongIndex = mSongs.size() - 1;
                }
            }
        }
    }

    private void shuffle() {
        if (mIsShuffle) {
            mIsShuffle = false;
            final Intent timeIntent = new Intent(Action.NOTSHUFFEL.getValue());
            sendBroadcast(timeIntent);
        } else {
            mIsShuffle = true;
            final Intent timeIntent = new Intent(Action.SHUFFEL.getValue());
            sendBroadcast(timeIntent);
        }
    }

    private void replay() {
        if (mIsAutoNext) {
            mIsAutoNext = false;
            final Intent timeIntent = new Intent(Action.NOTREPLAY.getValue());
            sendBroadcast(timeIntent);
        } else {
            mIsAutoNext = true;
            final Intent timeIntent = new Intent(Action.REPLAY.getValue());
            sendBroadcast(timeIntent);
        }
    }

    private void autoNext() {
        if (mSongs.size() != 0) {
            if (mIsAutoNext) {
                playSongWithPosition(mCurrentSongIndex);
            } else if (mIsShuffle) {
                Random rand = new Random();
                mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
                playSongWithPosition(mCurrentSongIndex);
            } else {
                if (mCurrentSongIndex < (mSongs.size() - 1)) {
                    playSongWithPosition(mCurrentSongIndex + 1);
                    mCurrentSongIndex = mCurrentSongIndex + 1;
                } else if (mCurrentSongIndex == (mSongs.size())) {
                    playSongWithPosition(0);
                    mCurrentSongIndex = 0;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
        super.onDestroy();
    }

    private void random() {
        Random rand = new Random();
        mCurrentSongIndex = rand.nextInt((mSongs.size() - 1) + 1);
        playSongWithPosition(mCurrentSongIndex);
    }

    private void handleCalling() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    mMediaPlayer.pause();
                    mCountDownTimer.cancel();
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    if (mMediaPlayer != null) {
                        if (!mMediaPlayer.isPlaying() && mIsPause) {
                            mIsPause = false;
                            mMediaPlayer.start();
                            mCountDownTimer.start();
                        }
                    }
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                        mCountDownTimer.cancel();
                    }
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
