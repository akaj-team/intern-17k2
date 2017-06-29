package vn.asiantech.internship.drawer.day16.ui;


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
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.ImagePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {

    private static final String FIELD_NAME = "mScroller";

    private View mView;

    private Handler mHandler = new Handler();
    private boolean isVisible;
    private int mNumPage;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_second, container, false);
        if (isVisible && getUserVisibleHint()) {
            loadData();
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
        isVisible = isVisibleToUser;
    }

    private void loadData() {
        if (mView != null) {
            final ViewPager viewPager = (ViewPager) mView.findViewById(R.id.viewPagerTabItem);
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.img_binhdinh);
            images.add(R.drawable.img_sunwheel);
            images.add(R.drawable.img_caurong);
            images.add(R.drawable.img_biendanang);
            mNumPage = images.size() - 1;
            ImagePagerAdapter adapter = new ImagePagerAdapter(images);
            viewPager.setAdapter(adapter);
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            changeDurationViewpager(viewPager);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem((viewPager.getCurrentItem() == mNumPage) ?
                            0 : viewPager.getCurrentItem() + 1);
                    mHandler.postDelayed(this, 7000);
                }
            }, 3000);
            isVisible = true;
        }
    }

    private void changeDurationViewpager(ViewPager viewPager) {
        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField(FIELD_NAME);
            field.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), interpolator);
            field.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            Log.e("NoSuchFieldEx", e.toString());
        } catch (IllegalArgumentException e) {
            Log.e("IllegalArgumentEx", e.toString());
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessEx", e.toString());
        }
    }

    /**
     * FixedSpeedScroller
     */
    class FixedSpeedScroller extends Scroller {

        private int mDuration = 5000;

        FixedSpeedScroller(Context context, Interpolator interpolator) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        isVisible = false;
    }
}
