package vn.asiantech.internship.exday19;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 02-07-2017.
 */
public class NotificationServiceMusic extends Service {
    private static final String TAG = NotificationServiceMusic.class.getSimpleName();
    private MediaPlayer mMediaPlayer;
    private Uri mUri;
    private String mUrlImage;
    private String mUrl;
    private int mLength;
    private boolean mIsShuffle;
    private boolean mIsRepeat;
    private CountDownTimer mCountDownTimer;
    private ArrayList<MusicItem> mMusicItems;
    private int mPosition;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ArrayList<MusicItem> songs = intent.getParcelableArrayListExtra(MusicActivity.KEY_MUSIC);
            if (songs != null) {
                mMusicItems = songs;
            }
            mPosition = intent.getIntExtra(MusicActivity.KEY_POSITION, -1);
            if (mPosition > -1) {
                startSong();
            }
            mUrl = intent.getStringExtra("url");
            mIsShuffle = intent.getBooleanExtra(PlayMusicFragment.KEY_SHUFFLE, false);
            Log.d("tag", "onStartCommand: " + mIsShuffle);
        }
        Log.d(TAG, "onStartCommand: " + mUrl);
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.PLAY.getValue())) {
                Intent timeIntent = new Intent(NotificationServiceMusic.this, PlayMusicFragment.class);
                if (mMediaPlayer.isPlaying()) {
                    timeIntent.putExtra("play", R.drawable.play);
                    mMediaPlayer.pause();
                } else {
                    timeIntent.putExtra("pause", R.drawable.pause);
                    mMediaPlayer.start();
                }
            } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                playNextSong();
                startSong();
            } else if (intent.getAction().equals(Action.PREV.getValue())) {
                playPreviousSong();
                startSong();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                playPreviousSong();
                startSong();
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                mMediaPlayer.pause();
                mLength = mMediaPlayer.getCurrentPosition();
            } else if (intent.getAction().equals(Action.START.getValue())) {
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setDataSource(mUrl);
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        showNotification();
                        mMediaPlayer.start();
                    }
                });

            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                mMediaPlayer.seekTo(mLength);
                mMediaPlayer.start();
            } else if (intent.getAction().equals(Action.STOP.getValue())) {
                stopForeground(true);
                stopSelf();
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                int time = intent.getIntExtra("chooseTime", 0);
                mMediaPlayer.seekTo(time);
                mMediaPlayer.start();
            }
        }
        return START_STICKY;
    }

    public void initShuffleNext() {
        if (!mIsShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            for (int i = 0; i < mMusicItems.size(); i++) {
                mPosition = rand.nextInt(mMusicItems.size());
            }
        } else {
            mPosition = (mPosition + 1) % mMusicItems.size();
        }
    }

    public void initRepeat() {
        if (mIsRepeat) {
            mMediaPlayer.setLooping(true);
            Log.d("tag", "initRepeat: " + mIsRepeat);
        } else {
            mMediaPlayer.setLooping(false);
            Log.d("tag", "initRepeat: 1111" + mIsRepeat);
        }
    }

    private void startSong() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mUrl = mMusicItems.get(mPosition).getUrl();
        mUri = Uri.parse(mUrl);
        mUrlImage = mMusicItems.get(mPosition).getImage();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mUri);
        Intent sendDataIntent = new Intent(Action.CHANGE_SONG.getValue());
        sendDataIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
        sendBroadcast(sendDataIntent);
        mMediaPlayer.start();
        setCountDownTimer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playNextSong();
                startSong();
            }
        });
    }

    private void playNextSong() {
        initShuffleNext();
        initRepeat();
    }

    public void playPreviousSong() {
        initRepeat();
        if (mIsShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            for (int i = 0; i < mMusicItems.size(); i++) {
                mPosition = rand.nextInt(mMusicItems.size());
            }
        } else {
            mPosition = (mPosition - 1 < 0) ? mMusicItems.size() - 1 : mPosition - 1;
        }
    }

    public void setCountDownTimer() {
        mCountDownTimer = new CountDownTimer(mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition(), 1000) {
            @Override
            public void onTick(long l) {
                Intent timeIntent = new Intent(Action.SEEK.getValue());
                timeIntent.putExtra(PlayMusicFragment.KEY_DURATION, mMediaPlayer.getDuration());
                timeIntent.putExtra(PlayMusicFragment.KEY_CURRENT_POSITION, mMediaPlayer.getCurrentPosition());
                timeIntent.putExtra(PlayMusicFragment.KEY_PLAYING, mMediaPlayer.isPlaying());
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    private void showNotification() {
        Intent notificationIntent = new Intent(this, PlayMusicFragment.class);

        notificationIntent.setAction(Action.INTENT.getValue());
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent playIntent = new Intent(this, NotificationServiceMusic.class);
        playIntent.setAction(Action.START.getValue());
        PendingIntent playPIntent = PendingIntent.getService(this, 0,
                playIntent, 0);
        Intent resumeIntent = new Intent(this, NotificationServiceMusic.class);
        resumeIntent.setAction(Action.RESUME.getValue());
        PendingIntent resumePIntent = PendingIntent.getService(this, 0,
                resumeIntent, 0);

        Intent pauseIntent = new Intent(this, NotificationServiceMusic.class);
        pauseIntent.setAction(Action.PAUSE.getValue());
        PendingIntent pausePIntent = PendingIntent.getService(this, 0,
                pauseIntent, 0);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notification);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(" Music Player")
                .setContentText("Song name....")
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(Bitmap.createScaledBitmap(bm, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "Play",
                        playPIntent)
                .addAction(android.R.drawable.ic_media_play, "Resume",
                        resumePIntent)
                .addAction(android.R.drawable.ic_media_next, "Pause",
                        pausePIntent).build();
        startForeground(111,
                notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                Log.d(TAG, "onDestroy: " + e);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
