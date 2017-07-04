package vn.asiantech.internship.day20.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day20.ui.MusicFragment;

public class MusicService extends Service {

    public static final String TAG = "at-dinhvo";

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";

    private ArrayList<Integer> raws = new ArrayList();

    private String mUrl;
    private MediaPlayer mMediaPlayer;
    private int mLength;
    private boolean isPause = false;
    private int currentPost = 0;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("url") != null) {
                mUrl = intent.getStringExtra("url");
                switch (intent.getAction()) {
                    case ACTION_PLAY:
                        // pause music
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                            isPause = true;
                            mLength = mMediaPlayer.getCurrentPosition();
                            Log.e(TAG, "pause: " + mLength);
                        } else if (isPause) { // resume music
                            mMediaPlayer.seekTo(mLength);
                            Log.e(TAG, "resume: " + mLength);
                            mMediaPlayer.start();
                        } else { // play music
                            Log.e(TAG, "play: " + mLength);
                            mMediaPlayer.start();
                        }
                        handlerProgress();
                        showNotification();
                        break;
                    case ACTION_NEXT:
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.release();
                        }
                        currentPost = (currentPost == raws.size() - 1) ? 0 : currentPost + 1;
                        Log.e(TAG, "next: " + currentPost);
                        mMediaPlayer = MediaPlayer.create(getApplicationContext(), raws.get(currentPost));
                        mMediaPlayer.start();
                        handlerProgress();
                        break;
                    case ACTION_PREVIOUS:
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.release();
                        }
                        currentPost = (currentPost == 0) ? raws.size() - 1 : currentPost - 1;
                        Log.e(TAG, "previous: " + currentPost);
                        mMediaPlayer = MediaPlayer.create(getApplicationContext(), raws.get(currentPost));
                        mMediaPlayer.start();
                        handlerProgress();
                        break;
                    case ACTION_SHUFFLE:
                        break;
                    case ACTION_AUTONEXT:
                        break;
                    /*case Intent.ACTION_SCREEN_OFF:
                        showNotification();*/
                    default:

                }
            }
        }
    };

    private void handlerProgress() {
        final Intent timeIntent = new Intent();
        timeIntent.setAction(MusicFragment.CURRENT_TIME);
        CountDownTimer countDownTimer = new CountDownTimer(mMediaPlayer.getDuration(), 1000) {
            @Override
            public void onTick(long l) {
                timeIntent.putExtra("time", mMediaPlayer.getDuration());
                timeIntent.putExtra("second", mMediaPlayer.getCurrentPosition());
                sendBroadcast(timeIntent);
                Log.e(TAG, "onTick: " + mMediaPlayer.getCurrentPosition());
            }

            @Override
            public void onFinish() {
            }
        };
        countDownTimer.start();
    }

    private void showNotification() {
        Intent notificationIntent = new Intent(this, MusicFragment.class);

        notificationIntent.setAction("SHOW");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent playPIntent = PendingIntent.getService(this, 0,
                playIntent, 0);
        Intent resumeIntent = new Intent(this, MusicService.class);
        resumeIntent.setAction(ACTION_NEXT);
        PendingIntent resumePIntent = PendingIntent.getService(this, 0,
                resumeIntent, 0);

        Intent pauseIntent = new Intent(this, MusicService.class);
        pauseIntent.setAction(ACTION_PREVIOUS);
        PendingIntent pausePIntent = PendingIntent.getService(this, 0,
                pauseIntent, 0);

        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_troll);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(" Music Player")
                .setContentText("Song name....")
                .setSmallIcon(R.drawable.ic_troll)
                .setLargeIcon(Bitmap.createScaledBitmap(bm, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_play, "Play",
                        playPIntent)
                .addAction(android.R.drawable.ic_media_pause, "Resume",
                        resumePIntent)
                .addAction(android.R.drawable.ic_media_next, "Pause",
                        pausePIntent).build();
        startForeground(111,
                notification);
    }

    public MusicService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PLAY);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PREVIOUS);
        filter.addAction(ACTION_RESUME);
        registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AAAAA", "onStartCommand: ");
//        mMediaPlayer = new MediaPlayer();
//        {R.raw.animals, R.raw.maps, R.raw.onecallaway, R.raw.onemorenight};
        raws.add(R.raw.animals);
        raws.add(R.raw.maps);
        raws.add(R.raw.onecallaway);
        raws.add(R.raw.onemorenight);
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), raws.get(0));
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}