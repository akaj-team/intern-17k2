package vn.asiantech.internship;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.asiantech.internship.fragment.PlayMusicFragment;
import vn.asiantech.internship.models.Action;

/**
 * Created by ducle on 07/07/2017.
 */

public class MusicBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction() == Action.START.getValue()) {
                String url = intent.getStringExtra(PlayMusicFragment.KEY_URL);
                if (this instanceof OnChangeMediaPlayerListener) {
                    ((OnChangeMediaPlayerListener) this).onStart(url);
                }
            }
            if (intent.getAction() == Action.PAUSE.getValue()) {

            }
            if (intent.getAction() == Action.RESUME.getValue()) {

            }
        }
    }

    public interface OnChangeMediaPlayerListener {
        void onStart(String url);
    }
}
