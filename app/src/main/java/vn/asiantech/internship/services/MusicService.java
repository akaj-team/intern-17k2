package vn.asiantech.internship.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Song;
import vn.asiantech.internship.ui.music.Action;

/**
 *
 * Created by quanghai on 30/06/2017.
 */
public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPLayer;
    private List<Song> mSongs;
    private int mCurrentPosition;
    private boolean mIsShuffle;
    private boolean mIsReplay;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mMediaPLayer = new MediaPlayer();
        mMediaPLayer.setOnPreparedListener(this);
        mMediaPLayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.getAction().equals(Action.START.getValue())) {
                getBundle(intent);
                playSong(mSongs, mCurrentPosition);
            } else if (intent.getAction().equals(Action.PREVIOUS_SONG.getValue())) {
                if (mIsShuffle) {
                    shuffleSongs();
                } else {
                    if (mCurrentPosition > 0) {
                        mCurrentPosition--;
                    }
                }
                playSong(mSongs, mCurrentPosition);
                sendBroadcastSong(Action.PREVIOUS_SONG.getValue());
            } else if (intent.getAction().equals(Action.NEXT_SONG.getValue())) {
                if (mIsShuffle) {
                    shuffleSongs();
                } else {
                    if (mCurrentPosition < mSongs.size() - 1) {
                        mCurrentPosition++;
                    }
                }
                playSong(mSongs, mCurrentPosition);
                sendBroadcastSong(Action.NEXT_SONG.getValue());

            } else if (intent.getAction().equals(Action.PAUSE.getValue())) {
                if (mMediaPLayer.isPlaying()) {
                    mMediaPLayer.pause();
                    Log.d("xxx", "onStartCommand: pause " + mMediaPLayer.isPlaying());
                }
            } else if (intent.getAction().equals(Action.RESUME.getValue())) {
                if (!mMediaPLayer.isPlaying()) {
                    mMediaPLayer.start();
                    Log.d("xxx", "onStartCommand: resume" + mMediaPLayer.isPlaying());
                }
            } else if (intent.getAction().equals(Action.SEEK_TO.getValue())) {
                int position = intent.getIntExtra(Action.SEEK_TO.getValue(), 0);
                mMediaPLayer.seekTo(position);
                mMediaPLayer.start();
            } else if (intent.getAction().equals(Action.SHUFFLE.getValue())) {
                mIsShuffle = intent.getBooleanExtra(Action.SHUFFLE.getValue(), false);
            } else if (intent.getAction().equals(Action.REPLAY.getValue())) {
                mIsReplay = intent.getBooleanExtra(Action.REPLAY.getValue(), false);
                Log.d("xxx", "replay: " + mIsReplay);
            }
        }
        return START_STICKY;
    }

    private void getBundle(Intent intent) {
        mSongs = intent.getParcelableArrayListExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue());
        mCurrentPosition = intent.getIntExtra(Action.KEY_BUNDLE_POSITION.getValue(), -1);
    }

    private void shuffleSongs() {
        Random random = new Random();
        mCurrentPosition = random.nextInt(mSongs.size() - 1);
    }

    private void playSong(List<Song> songs, int position) {
        mMediaPLayer.reset();
        Song song = songs.get(position);
        int id = song.getId();
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        try {
            mMediaPLayer.setDataSource(this, uri);
        } catch (IOException e) {
            Log.e("xxx", "playSong: " + e.getMessage());
        }
        mMediaPLayer.prepareAsync();
    }

    private void showNotification() {
        Song song = mSongs.get(mCurrentPosition);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tvNotificationSongName, song.getTitle());
        remoteViews.setTextViewText(R.id.tvNotificationArtist, song.getArtist());
        remoteViews.setTextColor(R.id.tvNotificationSongName, Color.BLACK);
        remoteViews.setTextColor(R.id.tvNotificationArtist, Color.BLACK);

        Intent nextIntent = new Intent(this, MusicService.class);
        nextIntent.setAction(Action.NEXT_SONG.getValue());
        PendingIntent nextPendingIntent = PendingIntent.getService(this, 0, nextIntent, 0);

        Intent previousIntent = new Intent(this, MusicService.class);
        previousIntent.setAction(Action.PREVIOUS_SONG.getValue());
        PendingIntent previousPendingIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = new Intent(this, MusicService.class);
        if (mMediaPLayer.isPlaying()) {
            Log.d("xxx", "showNotification: playing");
            playIntent.setAction(Action.PAUSE.getValue());
        } else {
            Log.d("xxx", "showNotification: nononono");
            playIntent.setAction(Action.RESUME.getValue());
        }
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, 0);

        Notification builder = new NotificationCompat.Builder(this)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setContentTitle(mSongs.get(mCurrentPosition).getTitle())
                .addAction(R.drawable.ic_skip_previous_black_24dp, null, previousPendingIntent)
                .addAction(R.drawable.ic_play_arrow_black_24dp, null, playPendingIntent)
                .addAction(R.drawable.ic_skip_next_black_24dp, null, nextPendingIntent)
                .build();
        startForeground(1, builder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPLayer.stop();
        stopForeground(true);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        showNotification();
        mediaPlayer.start();
        final Intent intent = new Intent(Action.SEEK.getValue());
        CountDownTimer countDownTimer = new CountDownTimer(mMediaPLayer.getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                intent.putExtra(Action.KEY_DURATION.getValue(), mMediaPLayer.getDuration());
                intent.putExtra(Action.KEY_CURRENT_TIME.getValue(), mMediaPLayer.getCurrentPosition());
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    private void sendBroadcastSong(String action) {
        Intent intent = new Intent(action);
        intent.putExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), mSongs.get(mCurrentPosition));
        sendBroadcast(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsReplay) {
            mp.setLooping(true);
            mp.start();
        } else {
            if (!mIsShuffle) {
                if (mCurrentPosition < mSongs.size() - 1) {
                    mCurrentPosition++;
                }
            }
            shuffleSongs();
            playSong(mSongs, mCurrentPosition);
            Intent intent = new Intent(Action.AUTO_NEXT.getValue());
            intent.putExtra(Action.KEY_BUNDLE_ARRAYLIST.getValue(), mSongs.get(mCurrentPosition));
            sendBroadcast(intent);
        }
    }
}