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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
public class PageBFragment extends Fragment {
    private ViewPagerBdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private static int currentPage = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_b, container, false);
        List<String> images = new ArrayList<>();
        images.add("http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg");
        images.add("http://vignette2.wikia.nocookie.net/runescape2/images/3/36/Lord_Amlodd_concept_art.jpg/revision/latest?cb=20140811105559");
        images.add("https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg");
        images.add("https://cdna.artstation.com/p/assets/images/images/002/854/562/large/jonas-lopez-moreno-jonaslopezmoreno-saitan-web.jpg?1466498557");
        images.add("http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg");

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPagerAdapter = new ViewPagerBdapter(getChildFragmentManager(), images);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        run();
        changeDurationScroll();
        return view;
    }

    private void run() {
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mViewPagerAdapter.getCount()) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 5000);
    }

    private void changeDurationScroll() {
        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            CustomDuration customDuration = new CustomDuration(mViewPager.getContext(), new AccelerateInterpolator());
            mScroller.set(mViewPager, customDuration);
        } catch (Exception e) {
            Log.i("changeDurationScroll: ", e.getMessage());
        }
    }
}
