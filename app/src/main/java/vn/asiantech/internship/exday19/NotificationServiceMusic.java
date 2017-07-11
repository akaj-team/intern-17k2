package vn.asiantech.internship.exday19;

import android.app.Notification;
import android.app.NotificationManager;
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
import android.widget.RemoteViews;

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
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private NotificationCompat.Builder mBuilder;
    private RemoteViews mRemoteViewsBig;
    private RemoteViews mRemoteViewsSmall;

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
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(Action.PLAY.getValue())) {
                startSong();
            } else if (intent.getAction().equals(Action.NEXT.getValue())) {
                playNextSong();
                startSong();
            } else if (intent.getAction().equals(Action.PREV.getValue())) {
                playPreviousSong();
                startSong();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mIsShuffle = intent.getBooleanExtra(PlayMusicFragment.KEY_SHUFFLE, false);
            } else if (intent.getAction().equals(Action.REPEAT.getValue())) {
                mIsRepeat = intent.getBooleanExtra(PlayMusicFragment.KEY_REPEAT, false);
            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                mMediaPlayer.pause();
                mLength = mMediaPlayer.getCurrentPosition();
            } else if (intent.getAction().equals(Action.START.getValue())) {
                mMediaPlayer = new MediaPlayer();
                if (mUrl != null) {
                    try {
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mMediaPlayer.setDataSource(mUrl);
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        Log.d(TAG, "null media: " + e);
                    }
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
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), mUri);
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
                if (mMediaPlayer.isPlaying()) {
                    timeIntent.putExtra(PlayMusicFragment.KEY_IMAGE, mUrlImage);
                }
                sendBroadcast(timeIntent);
                showNotification();
            }

            @Override
            public void onFinish() {
            }
        };
        mCountDownTimer.start();
    }

    private void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mRemoteViewsBig = new RemoteViews(getPackageName(), R.layout.notification_main);
        mRemoteViewsBig.setProgressBar(R.id.progressBar, mMediaPlayer.getDuration(), mMediaPlayer.getCurrentPosition(), false);
        mRemoteViewsBig.setTextViewText(R.id.tvSongName, mMusicItems.get(mPosition).getSongName());
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.album_art);
        mRemoteViewsBig.setImageViewBitmap(R.id.imgNotification, bm);

        Intent intentPlaySong = new Intent(this, NotificationServiceMusic.class);
        // play intent
        if (mMediaPlayer.isPlaying()) {
            intentPlaySong.setAction(Action.PAUSE.getValue());
            PendingIntent playSong = PendingIntent.getService(this, 0, intentPlaySong, 0);
            mRemoteViewsBig.setOnClickPendingIntent(R.id.imgPlay, playSong);
            mRemoteViewsBig.setImageViewResource(R.id.imgPlay, R.drawable.pause);
        } else if (!mMediaPlayer.isPlaying()) {
            intentPlaySong.setAction(Action.RESUME.getValue());
            PendingIntent playSong = PendingIntent.getService(this, 0, intentPlaySong, 0);
            mRemoteViewsBig.setOnClickPendingIntent(R.id.imgPlay, playSong);
            mRemoteViewsBig.setImageViewResource(R.id.imgPlay, R.drawable.play);
        }

        Intent intentPreviousSong = new Intent(this, NotificationServiceMusic.class);
        intentPreviousSong.setAction(Action.PREV.getValue());
        PendingIntent previousSong = PendingIntent.getService(this, 0, intentPreviousSong, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgPrevious, previousSong);

        Intent intentNextSong = new Intent(this, NotificationServiceMusic.class);
        intentNextSong.setAction(Action.NEXT.getValue());
        PendingIntent nextSong = PendingIntent.getService(this, 0, intentNextSong, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgNext, nextSong);

        Intent intentTurnOff = new Intent(this, NotificationServiceMusic.class);
        intentTurnOff.setAction(Action.STOP.getValue());
        PendingIntent turnOff = PendingIntent.getService(this, 0, intentTurnOff, 0);
        mRemoteViewsBig.setOnClickPendingIntent(R.id.imgTurnOff, turnOff);

        Intent notificationIntent = new Intent(getApplicationContext(), PlayMusicFragment.class);
        notificationIntent.putExtra(MusicActivity.KEY_POSITION, mPosition);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setSmallIcon(R.drawable.album_art)
                .setLargeIcon(Bitmap.createScaledBitmap(bm, 128, 128, false))
                .setAutoCancel(false)
                .setOngoing(true)
                .setCustomBigContentView(mRemoteViewsBig)
                .setContentIntent(pendingIntent);

        mNotificationManager.notify(111, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, NotificationServiceMusic.class);
        stopService(intent);
        mCountDownTimer.onFinish();
        stopForeground(true);
        stopSelf();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        super.onDestroy();
    }
}
