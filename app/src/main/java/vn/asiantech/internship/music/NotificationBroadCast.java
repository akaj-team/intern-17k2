package vn.asiantech.internship.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            contentView.setTextViewText(R.id.tvMusicTitleNotification, "Ten bai hat");
            contentView.setTextViewText(R.id.tvSingerNotification, "Ca si");
            Intent notificationIntent = new Intent(context, MusicActivity.class);
            notificationIntent.setAction("show");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_box_white_24dp);
            Notification notification = new NotificationCompat.Builder(context)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_add_box_white_24dp)
                    .setLargeIcon(Bitmap.createScaledBitmap(bm, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true).build();
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, notification);
        }
    }
}
