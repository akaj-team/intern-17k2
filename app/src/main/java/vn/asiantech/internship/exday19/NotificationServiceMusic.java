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
    private ArrayList<MusicItem> mMusicItems = new ArrayList<>();
    private int mPosition;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ArrayList<MusicItem> songs = intent.getParcelableArrayListExtra(MusicActivity.KEY_SEND);
            if (songs != null && mMusicItems.size() < 1) {
                mMusicItems = songs;
            }
            mPosition = intent.getIntExtra(MusicActivity.KEY_POSITION, mPosition);
            mUrl = intent.getStringExtra("url");
        }
        Log.d("tag", "onStartCommand: " + mUrl);
        //TODO set image for each song ahihi
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.PLAY.getValue())) {
            } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                playNextSong();
                startSong();
            } else if (intent.getAction().equals(Action.PREV.getValue())) {
                playPreviousSong();
                startSong();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mIsShuffle = intent.getBooleanExtra(PlayMusicFragment.KEY_SHUFFLE, false);
                Log.d("tag", "shuffle: " + mIsShuffle);
            } else if (intent.getAction().equals(Action.REPEAT.getValue())) {
                mIsRepeat = intent.getBooleanExtra(PlayMusicFragment.KEY_REPEAT, false);
                Log.d("tag", "repeat: " + mIsRepeat);
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
                    Log.d(TAG, "null media: " + e);
                }
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        showNotification();
                        mMediaPlayer.start();
                    }
                });
                startSong();
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

    private void startSong() {
        if (mPosition < 0) {
            stopForeground(true);
            mCountDownTimer.cancel();
            return;
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mUrl = mMusicItems.get(mPosition).getUrl();
        mUri = Uri.parse(mUrl);
        mUrlImage = mMusicItems.get(mPosition).getImage();
        Log.d("tag11", "startSong: " + mUrlImage);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mUri);
        Intent sendDataIntent = new Intent(NotificationServiceMusic.this, PlayMusicFragment.class);
        sendDataIntent.setAction(Action.START.getValue());
        sendDataIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
        sendDataIntent.putExtra(PlayMusicFragment.KEY_IMAGE, mUrlImage);
        startService(sendDataIntent);
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
        if (!mIsRepeat) {
            if (mIsShuffle) {
                // shuffle is on - play a random song
                Random rand = new Random();
                mPosition = rand.nextInt(mMusicItems.size());
            } else {
                mPosition = (mPosition + 1) % mMusicItems.size();
            }
        } else {
            mPosition = (mPosition) % mMusicItems.size();
        }
    }

    public void playPreviousSong() {
        if (!mIsRepeat) {
            if (mIsShuffle) {
                // shuffle is on - play a random song
                Random rand = new Random();
                int position;
                do {
                    position = rand.nextInt(mMusicItems.size());
                } while (mPosition == position);
                mPosition = position;
            } else {
                mPosition = (mPosition - 1 < 0) ? mMusicItems.size() - 1 : mPosition - 1;
            }
        } else {
            mPosition = (mPosition) % mMusicItems.size();
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
        notificationIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);

        notificationIntent.setAction(Action.INTENT.getValue());
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent playIntent = new Intent(this, NotificationServiceMusic.class);
        if (mMediaPlayer.isPlaying()) {
            playIntent.setAction(Action.PAUSE.getValue());
        } else {
            playIntent.setAction(Action.RESUME.getValue());
        }
        PendingIntent playPIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, NotificationServiceMusic.class);
        nextIntent.setAction(Action.NEXT.getValue());
        PendingIntent nextPIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent previousIntent = new Intent(this, NotificationServiceMusic.class);
        previousIntent.setAction(Action.PREV.getValue());
        PendingIntent previousPIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_notification);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.content_songname))
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(Bitmap.createScaledBitmap(bm, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous, "previous",
                        previousPIntent)
                .addAction(android.R.drawable.ic_media_play, "play",
                        playPIntent)
                .addAction(android.R.drawable.ic_media_next, "next",
                        nextPIntent).build();
        startForeground(111,
                notification);
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, NotificationServiceMusic.class);
        stopService(intent);
        mCountDownTimer.cancel();
        stopForeground(true);
        stopSelf();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
