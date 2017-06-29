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
import vn.asiantech.internship.myultils.ChangeDurationTimeViewPager;
import vn.asiantech.internship.adapters.SpainStarAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class SpainStarFragment extends Fragment {

    private ViewPager mViewPagerSpainStar;
    private SpainStarAdapter mAdapter;
    private int[] mSpainStars = {R.drawable.bg_torres, R.drawable.bg_ramos, R.drawable.bg_iniesta};
    private Handler mHandler;
    private int mCurrentItem;
    private boolean mInSliding = true;
    private Thread mThreadSlide;

    public static SpainStarFragment getNewInstance() {
        return new SpainStarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spain_star, container, false);
        mViewPagerSpainStar = (ViewPager) view.findViewById(R.id.viewPagerSpainStar);
        mAdapter = new SpainStarAdapter(mSpainStars);
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
        return view;
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
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Interpolator interpolator = new AccelerateInterpolator();
            ChangeDurationTimeViewPager changeDurationTimeViewPager = new ChangeDurationTimeViewPager(getContext(), interpolator);
            scroller.set(mViewPagerSpainStar, changeDurationTimeViewPager);
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
                        mInSliding = false;
                        return;
                    }
                    if (mInSliding) {
                        mViewPagerSpainStar.setCurrentItem(mCurrentItem++);
                        mHandler.postDelayed(this, 5000);
                    }
                }
            });
        }
        mHandler.postDelayed(mThreadSlide, 5000);
    }
}
