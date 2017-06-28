package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.tablayout.transformer.ChangeDurationTimeViewPager;
import vn.asiantech.internship.ui.tablayout.transformer.TabletTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerSecondFragment extends Fragment {

    private ViewPager mViewPager;
    private MyTabView mMyTabView;
    private ViewPagerSecondAdapter mViewPagerSecondAdapter;

    private Handler mHandler;
    private Thread mThread;
    private boolean mIsSliding;
    private boolean mIsHaveTabSelected;
    private float mTabSelected;
    private float mLocationOld;

    private String[] mUrls = {
            "https://s-media-cache-ak0.pinimg.com/736x/bc/82/d6/bc82d6709eaa6921d32f25b2567cdc6d.jpg",
            "https://mfiles.alphacoders.com/579/579712.jpg",
            "https://s-media-cache-ak0.pinimg.com/736x/33/cd/16/33cd1631698f5e78a3e676f9fc222c59.jpg",
            "https://mfiles.alphacoders.com/600/600454.jpg",
            "http://p1.i.ntere.st/9133e51d84e89162372fc45429844911_480.jpg"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_pager_second, container, false);

        mIsSliding = true;
        mHandler = new Handler();

        mMyTabView = (MyTabView) v.findViewById(R.id.gridView);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mViewPagerSecondAdapter = new ViewPagerSecondAdapter(getChildFragmentManager(), mUrls);

        mLocationOld = mViewPager.getWidth() / 2;
        slowSlider();
        autoSlider();
        mMyTabView.onClickItem(new MyTabView.OnGridViewListener() {
            @Override
            public void onItemClick(float position) {
                mViewPager.setCurrentItem((int) position);
                mTabSelected = position;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mMyTabView.setTabSelected(position);
                mTabSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    mMyTabView.setOnTouch(true);
                    mIsSliding = false;
                    mHandler.removeCallbacks(mThread);
                    if (event.getX() > mLocationOld) {
                        mTabSelected -= 0.01f;
                        mMyTabView.setTabSelected(mTabSelected);
                    } else {
                        mTabSelected += 0.01f;
                        mMyTabView.setTabSelected(mTabSelected);
                    }
                    mLocationOld = event.getX();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mIsSliding = true;
                    mMyTabView.setOnTouch(false);
                    mLocationOld = mViewPager.getWidth() / 2;
                    mTabSelected = getInt(mTabSelected);
                    mMyTabView.setTabSelected(mTabSelected);
                    return true;
                }
                return false;
            }
        });
        return v;
    }

    private Integer getInt(Float f) {
        DecimalFormat df = new DecimalFormat("0");
        String str = df.format(f);
        return Integer.valueOf(str);
    }

    private void slowSlider() {
        try {
            Field mScroller;
            ViewPager.class.getDeclaredField("mScroller");
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator interpolator = new AccelerateInterpolator();
            ChangeDurationTimeViewPager scroller = new ChangeDurationTimeViewPager(getContext(), interpolator);
            scroller.setDuration(5000);
            mScroller.set(mViewPager, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Log.i("tag11", e.getMessage());
        }
    }

    private void autoSlider() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (mTabSelected == 5) {
                    mTabSelected = 0;
                    mViewPager.setCurrentItem((int) mTabSelected);
                    mIsSliding = false;
                    return;
                }
                if (mIsSliding) {
                    mViewPager.setCurrentItem((int) mTabSelected++);
                    mHandler.postDelayed(this, 5500);
                }
            }
        });
        mHandler.postDelayed(mThread, 1000);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mIsHaveTabSelected) {
            mViewPager.setAdapter(mViewPagerSecondAdapter);
            mViewPager.setCurrentItem(2); // Default item in the first time open
            mViewPager.setPageTransformer(false, new TabletTransformer());
            mIsHaveTabSelected = true;
        }
    }
}
