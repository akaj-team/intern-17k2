package vn.asiantech.internship.drawer.day16.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.reflect.Field;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.PageAdapter;

import static vn.asiantech.internship.R.id.tabLayout;

public class TabActivity extends AppCompatActivity {

    private static final String FIELD_NAME = "mScroller";

    private TabLayout mTabLayout;
    private ViewPager mViewpager;
    private Handler mHandler = new Handler();
    private int mNumPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initUI();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewpager.setCurrentItem((mViewpager.getCurrentItem() == mNumPage) ?
                        0 : mViewpager.getCurrentItem() + 1);
                mHandler.postDelayed(this, 5000);
            }
        }, 1000);
    }

    private void initUI() {
        mViewpager = (ViewPager) findViewById(R.id.viewPagerTab);
        mTabLayout = (TabLayout) findViewById(tabLayout);
        setupViewpager(mViewpager);
        mTabLayout.setupWithViewPager(mViewpager);
        setupTabLayout("Search", 0, R.drawable.ic_search_blue_grey_800_24dp);
        setupTabLayout("Place", 1, R.drawable.ic_place_blue_grey_800_24dp);
        setupTabLayout("Profile", 2, R.drawable.ic_person_pin_blue_grey_800_24dp);
    }

    private void setupTabLayout(String name, int index, int image) {
        View tabItem = LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
        TextView tvItem = (TextView) tabItem.findViewById(R.id.tvTab);
        tvItem.setText(name);
        tvItem.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);
        TabLayout.Tab tab = mTabLayout.getTabAt(index);
        if (tab != null) {
            tab.setCustomView(tabItem);
        }
        createAnimateTab(mTabLayout);
    }

    private void createAnimateTab(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int i = 0; i < tabsCount; i++) {
            int delay = (i * 150) + 750;
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(i);
            vgTab.setScaleX(0f);
            vgTab.setScaleY(0f);
            vgTab.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(delay)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(750)
                    .start();
        }
    }

    private void setupViewpager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        ThirdFragment thirdFragment = new ThirdFragment();
        secondFragment.setUserVisibleHint(true);
        thirdFragment.setUserVisibleHint(true);
        adapter.addFragment(firstFragment);
        adapter.addFragment(secondFragment);
        adapter.addFragment(thirdFragment);
        mNumPage = adapter.getCount() - 1;
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        changeDurationViewpager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:

                        break;
                    case 2:

                }
                Log.e("AAA", "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    private class FixedSpeedScroller extends Scroller {

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
}
