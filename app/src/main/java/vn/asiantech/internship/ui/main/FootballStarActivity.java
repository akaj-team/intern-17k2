package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.ultils.ChangeDurationTimeViewPager;
import vn.asiantech.internship.ultils.DepthPageTransformer;
import vn.asiantech.internship.ultils.MyCustomTab;
import vn.asiantech.internship.adapters.FootballStarAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class FootballStarActivity extends AppCompatActivity {

    private int[] mTabIcons = {R.drawable.ic_messi, R.drawable.ic_spain, R.drawable.ic_ronaldo, R.drawable.ic_reus, R.drawable.ic_kaka};

    private ViewPager mViewPagerFootballStar;
    private TabLayout mTabLayout;

    private FootballStarAdapter mAdapter;
    private Handler mHandler;
    private int mCurrentItem = 0;
    private boolean inSliding = true;
    private Thread mThreadSlide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_star);

        mAdapter = new FootballStarAdapter(getSupportFragmentManager());
        mViewPagerFootballStar = (ViewPager) findViewById(R.id.recyclerViewFootballStar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPagerFootballStar.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPagerFootballStar);
        try {
            Field mScroller;
            ViewPager.class.getDeclaredField("mScroller");
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator interpolator = new AccelerateInterpolator();
            ChangeDurationTimeViewPager scroller = new ChangeDurationTimeViewPager(this, interpolator);
            scroller.setDuration(5000);
            mScroller.set(mViewPagerFootballStar, scroller);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            Log.i("tag11", e.getMessage());
        }
        customTab();
        mHandler = new Handler();
        mThreadSlide = new Thread(new Runnable() {
            @Override
            public void run() {
                if (mCurrentItem == 5) {
                    mCurrentItem = 0;
                    mViewPagerFootballStar.setCurrentItem(mCurrentItem);
                    inSliding = false;
                    return;
                }
                if (inSliding) {
                    mViewPagerFootballStar.setCurrentItem(mCurrentItem++);
                    mHandler.postDelayed(this, 5000);
                }
            }
        });
        mHandler.postDelayed(mThreadSlide, 5000);
        mViewPagerFootballStar.setPageTransformer(true, new DepthPageTransformer());
    }

    private void customTab() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.tablayout_custom, null, false);
            MyCustomTab myCustomTab = (MyCustomTab) view.findViewById(R.id.myView);
            if (i == 0) {
                myCustomTab.setSelected(true);
            }
            CircleImageView icon = (CircleImageView) view.findViewById(R.id.imgTabIcon);
            icon.setImageResource(mTabIcons[i]);
            TextView tabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
            tabTitle.setText(mAdapter.getPageTitle(i));
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            final int position = i;
            if (tab != null) {
                tab.setCustomView(view);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inSliding = false;
                    mViewPagerFootballStar.setCurrentItem(position);
                    mHandler.removeCallbacks(mThreadSlide);

                }
            });
        }
    }

}

