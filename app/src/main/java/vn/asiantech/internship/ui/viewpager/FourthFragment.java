package vn.asiantech.internship.ui.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.eftimoff.viewpagertransformers.CubeOutTransformer;

import java.lang.reflect.Field;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.FourthAdapter;

/**
 *
 * Created by Hai on 6/28/2017.
 */
public class FourthFragment extends Fragment {
    private static final String NAME = "mScroller";

    private ViewPager mViewPager;
    private FourthAdapter mAdapter;
    private int[] mImageResources = {R.drawable.img_4, R.drawable.img_5, R.drawable.img_6};
    private Handler mHandler;
    private Runnable mRunnable;
    private int mPage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewPager = (ViewPager) inflater.inflate(R.layout.fragment_fourth, container, false);
        mAdapter = new FourthAdapter(mImageResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new CubeOutTransformer());
        setScroll(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getCount() == mPage) {
                    mPage = 0;
                } else {
                    mPage++;
                }
                mViewPager.setCurrentItem(mPage);
                mHandler.postDelayed(mRunnable, 3000);
            }
        };
        mHandler.postDelayed(mRunnable, 3000);
        return mViewPager;
    }

    private void setScroll(ViewPager viewPager) {
        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField(NAME);
            field.setAccessible(true);
            SpeedScroller scroller = new SpeedScroller(viewPager.getContext(), interpolator);
            field.set(viewPager, scroller);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    private class SpeedScroller extends Scroller {

        private int mDuration = 2000;

        SpeedScroller(Context context, Interpolator interpolator) {
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
