package vn.asiantech.internship.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
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
    private RemoteViews mNotificationView;
    private BroadcastReceiver mSeekBroadcastReceiver;
    private Intent mNotificationIntent;
    private PendingIntent mPendingIntent;
    private Intent mCancelIntent;
    private PendingIntent mCancelPendingIntent;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private Song mSong;
    private int mLength;
    private int mPosition;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            mNotificationView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            mNotificationIntent = new Intent(context, MusicActivity.class);
            mPendingIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, 0);
            mSeekBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mNotificationIntent.setAction(Action.INTENT.getValue());
                    mNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mLength = Integer.parseInt(intent.getStringExtra("time"));
                    mPosition = Integer.parseInt(intent.getStringExtra("second"));
                    mSong = intent.getExtras().getParcelable("song");

                    mNotificationView.setTextViewText(R.id.tvTitleMusicNotification, mSong.getName());
                    mNotificationView.setTextViewText(R.id.tvSingerNotification, mSong.getSinger());
                    mNotificationView.setImageViewResource(R.id.imgNotification, mSong.getImage());
                    mNotificationView.setTextViewText(R.id.tvCurrentTimeNotification, MusicUtil.milliSecondsToTimer(mPosition));
                    mNotificationView.setTextViewText(R.id.tvTotalTimeNotification, MusicUtil.milliSecondsToTimer(mLength));
                    mNotificationView.setProgressBar(R.id.progressBar, mLength, mPosition, false);

                    mCancelIntent = new Intent(context, MusicService.class);
                    mCancelIntent.setAction(Action.CANCEL.getValue());
                    mCancelPendingIntent = PendingIntent.getService(context, 0, mCancelIntent, 0);
                    mNotificationView.setOnClickPendingIntent(R.id.imgCancel, mCancelPendingIntent);

                    mNotification = new NotificationCompat.Builder(context)
                            .setContent(mNotificationView)
                            .setSmallIcon(R.drawable.ic_music_note_white_48dp)
                            .setContentIntent(mPendingIntent)
                            .setOngoing(true).build();
                    mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mNotification);
                }
            };
        }
        IntentFilter seekFilter = new IntentFilter(Action.SEEK.getValue());
        context.registerReceiver(mSeekBroadcastReceiver, seekFilter);
    }
}
