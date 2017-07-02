package vn.asiantech.internship.ui.tablayout;

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
import vn.asiantech.internship.others.SpeedScroll;
import vn.asiantech.internship.others.ZoomOutPage;
import vn.asiantech.internship.ui.adapters.Tab2ViewPagerAdapter;

/**
 * Fragment second.
 * Created by AnhHuy on 27-Jun-17.
 */
public class TabSecondFragment extends Fragment {
    private final int[] mImages = {R.drawable.img_tab5, R.drawable.img_tab1, R.drawable.img_tab4,
            R.drawable.img_tab2, R.drawable.img_tab3};

    private ViewPager mViewPagerTab2;
    private int mCurrentPage;
    private Timer mTimer;

    public static TabSecondFragment newInstance() {
        return new TabSecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_with_another, container, false);
        mViewPagerTab2 = (ViewPager) view.findViewById(R.id.viewPagerTab2);

        initViewPager();
        changeDurationScroll();
        autoScroll();
        return view;
    }

    private void initViewPager() {
        mViewPagerTab2.setAdapter(new Tab2ViewPagerAdapter(getContext(), mImages));
        mViewPagerTab2.setPageTransformer(true, new ZoomOutPage());
    }

    private void autoScroll() {
        final long delay = 50;
        final long period = 5000;
        final Tab2ViewPagerAdapter adapter = new Tab2ViewPagerAdapter(getContext(), mImages);
        mTimer = new Timer();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mCurrentPage == adapter.getCount()) {
                    mCurrentPage = 0;
                    mViewPagerTab2.setCurrentItem(mCurrentPage);
                    mTimer.cancel();
                }
                mViewPagerTab2.setCurrentItem(mCurrentPage++, true);
            }
        };
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, delay, period);
    }

    private void changeDurationScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            SpeedScroll scroll = new SpeedScroll(mViewPagerTab2.getContext(), new AccelerateInterpolator());
            mScroller.set(mViewPagerTab2, scroll);
        } catch (Exception e) {
            Log.i("changeDurationScroll: ", e.getMessage());
        }
    }
}
