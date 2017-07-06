package vn.asiantech.internship.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Used to display main music fragment .
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-7-1
 */
public class MainFragment extends Fragment {
    private BroadcastReceiver mBroadcastReceiver;
    private TextView mTvName;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_music, container, false);
        ImageView imgMusicMain = (ImageView) view.findViewById(R.id.imgMusicMain);
        Animation animFade = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        imgMusicMain.startAnimation(animFade);
        mTvName = (TextView) view.findViewById(R.id.tvNameSong);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTvName.setText(intent.getStringExtra("songName"));
            }
        };

        IntentFilter mStartFilter = new IntentFilter(Action.UPDATE.getValue());
        getActivity().registerReceiver(mBroadcastReceiver, mStartFilter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }
}
