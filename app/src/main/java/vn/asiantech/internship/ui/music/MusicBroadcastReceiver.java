package vn.asiantech.internship.ui.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by Thanh Thien
 */
public class MusicBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Intent intentShow = new Intent(context, NotificationServiceMusic.class);
            intentShow.setAction(Action.SHOW.getValue());
            context.startService(intentShow);
        }
    }
}
