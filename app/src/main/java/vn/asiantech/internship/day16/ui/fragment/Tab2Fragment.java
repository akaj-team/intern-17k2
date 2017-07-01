package vn.asiantech.internship.day16.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.adapter.ViewPagerInAdapter;
import vn.asiantech.internship.day16.view.FadeTransformer;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class Tab2Fragment extends Fragment {
    private static final String TAG = Tab2Fragment.class.getSimpleName();
    private static final int TIME = 5000;

    public static Tab2Fragment init() {
        return new Tab2Fragment();
    }

    private ViewPager mViewPager;
    private ViewPagerInAdapter mViewPagerInAdapter;
    private int[] mImages = {
            R.mipmap.img_pug,
            R.mipmap.img_pug_1,
            R.mipmap.img_pug_2
    };

    private int mCurrentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPagerIn);
        mViewPager.setPageTransformer(false, new FadeTransformer());
        mViewPagerInAdapter = new ViewPagerInAdapter(mImages, getContext());
        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), interpolator);
            field.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException ignored) {
            Log.i(TAG, " Exception at Scroller  ");
        }
        final Handler handler = new Handler();
        final Timer mTimer = new Timer();
        final Runnable update = new Runnable() {
            public void run() {
                if (mCurrentPage == mImages.length) {
                    mCurrentPage = 0;
                } else {
                    mViewPager.setCurrentItem(mCurrentPage++, true);
                }
            }
        };
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 50, TIME);

        mViewPagerInAdapter.notifyDataSetChanged();
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mViewPager != null) {
                mViewPager.setAdapter(mViewPagerInAdapter);
            }
        }
    }

    /**
     * Create FixedSpeedScroller
     */
    private final class FixedSpeedScroller extends Scroller {

        FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, TIME);
        }
    }
}
