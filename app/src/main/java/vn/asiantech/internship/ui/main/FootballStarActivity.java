package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.Ultils.DepthPageTransformer;
import vn.asiantech.internship.adapters.FootballStarAdapter;

/**
 * Created by PC on 6/26/2017.
 */

public class FootballStarActivity extends AppCompatActivity {
    private ViewPager mViewPagerFootballStar;
    private TabLayout mTabLayout;
    private List<String> mStars;
    private FootballStarAdapter mAdapter;
    private Handler mHandler;
    private int mCurrentItem = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_star);
        mStars = new ArrayList<>();

        mAdapter = new FootballStarAdapter(getSupportFragmentManager());
        mViewPagerFootballStar = (ViewPager) findViewById(R.id.recyclerViewFootballStar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPagerFootballStar.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPagerFootballStar);
//        try {
//            Field mScroller;
//            ViewPager.class.getDeclaredField("mScroller");
//            mScroller = ViewPager.class.getDeclaredField("mScroller");
//            mScroller.setAccessible(true);
//            Interpolator interpolator = new AccelerateInterpolator();
//            ChangeDurationTimeViewPager scroller = new ChangeDurationTimeViewPager(this, interpolator);
//            scroller.setDuration(5000);
//            mScroller.set(mViewPagerFootballStar, scroller);
//        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//            Log.i("tag11", e.getMessage());
//        }
        customTab();
//        mHandler = new Handler();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (mCurrentItem == 5) {
//                    mCurrentItem = 0;
//                    mViewPagerFootballStar.setCurrentItem(mCurrentItem);
//                    return;
//                }
//                mViewPagerFootballStar.setCurrentItem(mCurrentItem++);
//                mHandler.postDelayed(this, 5000);
//            }
//        });
//        thread.start();
        mViewPagerFootballStar.setPageTransformer(true, new DepthPageTransformer());
    }

    private void customTab() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
//            ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.tablayout_custom, null, false);
//            mTabLayout.getTabAt(i).setCustomView(imageView);
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_custom, null, false);
            mTabLayout.getTabAt(i).setCustomView(view);
        }
    }

}

