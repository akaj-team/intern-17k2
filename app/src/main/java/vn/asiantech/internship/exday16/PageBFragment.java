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
    private ViewPagerBAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private int mCurrentPage;

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
        images.add("https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg");

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerBAdapter(getChildFragmentManager(), images);
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
                } else {
                    mViewPager.setCurrentItem(mCurrentPage++, true);
                    //TODO only one loop
//                    Log.d("aaa", "run: "+mCurrentPage);
                }
                Log.d("tag", "run: " + mCurrentPage);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 5000);
    }

    private void changeDurationScroll() {
        try {
            Field scroller;
            scroller = ViewPager.class.getDeclaredField("scroller");
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
