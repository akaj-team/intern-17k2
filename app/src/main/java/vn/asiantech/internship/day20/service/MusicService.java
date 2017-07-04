package vn.asiantech.internship.day20.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import vn.asiantech.internship.R;

public class MusicService extends Service {

    public static final String TAG = "at-dinhvo";

    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_RESUME = "resume";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_SHUFFLE = "shuffle";
    public static final String ACTION_AUTONEXT = "auto_next";

    private String mUrl;
    private MediaPlayer mMediaPlayer;
    private int mLength;
    private boolean isPause = false;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("url") != null) {
                mUrl = intent.getStringExtra("url");
                switch (intent.getAction()) {
                    case ACTION_PLAY:
                        // pause music
                        if(mMediaPlayer.isPlaying()){
                            mMediaPlayer.pause();
                            isPause = true;
                            mLength = mMediaPlayer.getCurrentPosition();
                            Log.e(TAG, "pause: " + mLength);
                        } else if (isPause){ // resume music
                            mMediaPlayer.seekTo(mLength);
                            Log.e(TAG, "resume: " + mLength);
                            mMediaPlayer.start();
                        }else { // play music
                            Log.e(TAG, "play: " + mLength);
                            mMediaPlayer.start();
                        }
                        break;
                    case ACTION_NEXT:

                        break;
                    case ACTION_PREVIOUS:
                        break;
                    case ACTION_SHUFFLE:
                        break;
                    case ACTION_AUTONEXT:
                        break;
                    default:

                }
            }
        }
    };

    /*private void showNotification() {
        Intent notificationIntent = new Intent(this, AudioActivity.class);

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
                R.drawable.ic_male);

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
    }*/

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
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.onecallaway);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}