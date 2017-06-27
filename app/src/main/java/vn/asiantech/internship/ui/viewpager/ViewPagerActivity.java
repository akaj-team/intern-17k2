package vn.asiantech.internship.ui.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.DepthPageTransformer;

import java.lang.reflect.Field;

import vn.asiantech.internship.R;

/**
 * Created by Hai on 6/27/2017.
 */

public class ViewPagerActivity extends AppCompatActivity {
    private static final String NAME = "mScroller";
    private TabLayout mTabLayout;
    private String[] mTitles = {"Page 1", "Page 2", "Page 3"};
    private int[] mImageResource = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3};
    private int[] mIconTitle = {R.drawable.ic_menu, R.drawable.ic_user, R.drawable.ic_plus};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(mImageResource, mTitles);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        setScroll(viewPager);
        mTabLayout.setupWithViewPager(viewPager);
        customTablayout();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setScroll(ViewPager viewPager) {
        Interpolator interpolator = new AccelerateInterpolator();
        try {
            Field field;
            field = ViewPager.class.getDeclaredField(NAME);
            field.setAccessible(true);
            SpeedScroller scroller = new SpeedScroller(viewPager.getContext(), interpolator);
            field.set(viewPager, scroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void customTablayout() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TextView tvTitle = new TextView(this);
            tvTitle.setText(mTitles[i]);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(mIconTitle[i]), null, null);
            tvTitle.setGravity(Gravity.CENTER);
            mTabLayout.getTabAt(i).setCustomView(tvTitle);
        }
    }

    private class SpeedScroller extends Scroller {

        private int mDuration = 5000;

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
