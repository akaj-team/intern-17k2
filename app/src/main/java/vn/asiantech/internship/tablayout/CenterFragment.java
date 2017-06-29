package vn.asiantech.internship.tablayout;

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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.asiantech.internship.R;

import static android.content.ContentValues.TAG;

/**
 * Used to collect and display viewpager on fragment center.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
public class CenterFragment extends Fragment {
    private final List<Integer> mImages = Arrays.asList(R.drawable.img_six, R.drawable.img_seven, R.drawable.img_eight);
    private int mCurrentPage;
    private Timer mTimer;
    private ViewPager mSmallViewPager;

    public static CenterFragment newInstance() {
        return new CenterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        mSmallViewPager = (ViewPager) view.findViewById(R.id.smallViewPager);
        final SmallAdapter adapter = new SmallAdapter(getActivity(), mImages);
        mSmallViewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        mTimer = new Timer();
        final Runnable update = new Runnable() {
            public void run() {
                if (mCurrentPage == adapter.getCount()) {
                    mCurrentPage = 0;
                    mSmallViewPager.setCurrentItem(mCurrentPage, true);
                    mTimer.cancel();
                } else {
                    mSmallViewPager.setCurrentItem(mCurrentPage++, true);
                }
            }
        };
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 50, 5000);

        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mSmallViewPager.getContext(), interpolator);
            field.set(mSmallViewPager, scroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            Log.e(TAG, e.toString());
        }
        return view;
    }

    /**
     * Used to set time move for viewpager
     */
    private final class FixedSpeedScroller extends Scroller {
        private final int mDuration = 5000;

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
