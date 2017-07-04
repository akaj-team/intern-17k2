package vn.asiantech.internship.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import vn.asiantech.internship.R;

/**
 * Used to get display notification.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class NotificationBroadCast extends BroadcastReceiver {
    private int mLength;
    private int mPosition;
    private final MusicTime mTime = new MusicTime();
    private RemoteViews mNotificationView;
    private BroadcastReceiver mSeekBroadcastReceiver;
    private Song mSong;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.d("aaaaaaaaa", "onReceive: ");
            mSeekBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Intent notificationIntent = new Intent(context, MusicActivity.class);
                    notificationIntent.setAction("intent");
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                    mLength = Integer.parseInt(intent.getStringExtra("time"));
                    mPosition = Integer.parseInt(intent.getStringExtra("second"));
                    mSong = intent.getExtras().getParcelable("song");
                    mNotificationView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
                    mNotificationView.setTextViewText(R.id.tvTitleMusicNotification, mSong.getName());
                    mNotificationView.setTextViewText(R.id.tvSingerNotification, mSong.getSinger());
                    mNotificationView.setImageViewResource(R.id.imgNotification, mSong.getImage());
                    mNotificationView.setTextViewText(R.id.tvCurrentTimeNotification, mTime.milliSecondsToTimer(mPosition));
                    mNotificationView.setTextViewText(R.id.tvTotalTimeNotification, mTime.milliSecondsToTimer(mLength));
                    mNotificationView.setProgressBar(R.id.progressBar, mLength, mPosition, false);
                    Notification notification = new NotificationCompat.Builder(context)
                            .setContent(mNotificationView)
                            .setSmallIcon(R.drawable.ic_music_note_white_48dp)
                            .setContentIntent(pendingIntent)
                            .setOngoing(true).build();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);
                }
            };
        }
        IntentFilter filter = new IntentFilter("seek");
        context.registerReceiver(mSeekBroadcastReceiver, filter);
    }
}
