package vn.asiantech.internship.exday16;

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

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
public class PageBFragment extends Fragment {
    private ViewPagerBAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private int mCurrentPage;
    private String mImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_b, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerBAdapter(getChildFragmentManager(), mImage);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        run();
        changeDurationScroll();
        return view;
    }

    private void run() {
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (mCurrentPage == mViewPagerAdapter.getCount()) {
                    mCurrentPage = -1;
                }
                if (mCurrentPage == -1) {
                    mViewPager.setCurrentItem(mCurrentPage, true);
                } else {
                    mViewPager.setCurrentItem(mCurrentPage++, true);
                }
                Log.d("tag", "run: " + mCurrentPage);
            }
        };
        final Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mCurrentPage != -1) {
                    handler.post(update);
                } else {
                    swipeTimer.cancel();
                }
            }
        }, 1000, 5000);
    }

    private void changeDurationScroll() {
        try {
            Field scroller;
            scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            CustomDuration customDuration = new CustomDuration(mViewPager.getContext(), new AccelerateInterpolator());
            scroller.set(mViewPager, customDuration);
        } catch (Exception e) {
            Log.i("changeDurationScroll: ", e.getMessage());
        }
    }

    public static PageBFragment newInstance() {
        return new PageBFragment();
    }
}
