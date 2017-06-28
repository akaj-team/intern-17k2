package vn.asiantech.internship.day16.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class Tab2Fragment extends Fragment {
    private ViewPager mViewPager;
    ViewPagerInAdapter mViewPagerInAdapter;
    private int[] mImages = {
            R.mipmap.img_pug,
            R.mipmap.img_pug_1,
            R.mipmap.img_pug_2
    };

    private int mCurrentPage = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPagerIn);
        mViewPagerInAdapter = new ViewPagerInAdapter(mImages, getContext());
        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField("field");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(), interpolator);
            field.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException ignored) {
        }

        final Handler handler = new Handler();
        final Timer mTimer = new Timer();
        final Runnable Update = new Runnable() {
            public void run() {
                if (mCurrentPage == mImages.length - 1) {
                    mCurrentPage = 0;
                    mTimer.cancel();
                }
                mViewPager.setCurrentItem(mCurrentPage++, true);
            }
        };
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 50, 5000);
        mViewPagerInAdapter.notifyDataSetChanged();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

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

    private class FixedSpeedScroller extends Scroller {
        private int mDuration = 5000;

        private FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }
    }
}
