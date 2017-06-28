package vn.asiantech.internship.ui.fragments;

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

import java.lang.reflect.Field;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ultil.ChangeDurationTimeViewPager;
import vn.asiantech.internship.adapters.SpainStarAdapter;


/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class SpainStarFragment extends Fragment {

    private View mView;
    private ViewPager mViewPagerSpainStar;
    private SpainStarAdapter mAdapter;
    private int[] spainStars = {R.drawable.bg_torres, R.drawable.bg_ramos, R.drawable.bg_iniesta};
    private Handler mHandler;
    private int mCurrentItem = 0;
    private boolean inSliding = true;
    private Thread mThreadSlide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_spain_star, container, false);
        mViewPagerSpainStar = (ViewPager) mView.findViewById(R.id.viewPagerSpainStar);
        mAdapter = new SpainStarAdapter(spainStars);
        mViewPagerSpainStar.setAdapter(mAdapter);
        changeDuration();
        autoSlide();

        mViewPagerSpainStar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void changeDuration() {
        try {
            Field mScroller;
            ViewPager.class.getDeclaredField("mScroller");
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator interpolator = new AccelerateInterpolator();
            ChangeDurationTimeViewPager scroller = new ChangeDurationTimeViewPager(getContext(), interpolator);
            scroller.setDuration(5000);
            mScroller.set(mViewPagerSpainStar, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    private void autoSlide() {
        mHandler = new Handler();
        if (mAdapter != null) {
            mThreadSlide = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (mCurrentItem == mAdapter.getCount()) {
                        mCurrentItem = 0;
                        mViewPagerSpainStar.setCurrentItem(mCurrentItem);
                        inSliding = false;
                        return;
                    }
                    if (inSliding) {
                        mViewPagerSpainStar.setCurrentItem(mCurrentItem++);
                        mHandler.postDelayed(this, 5000);
                    }
                }
            });
        }
        mHandler.postDelayed(mThreadSlide, 5000);
    }
}
