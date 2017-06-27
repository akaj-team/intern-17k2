package vn.asiantech.internship.drawer.day16.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.PageAdapter;

public class TabActivity extends AppCompatActivity {

    private static final String FIELD_NAME = "mScroller";
    private static final String TAB_ITEM[] = {"Image0", "Image1", "Image2"};

    private TabLayout mTabLayout;
    private ViewPager mViewpager;

    private List<Integer> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initUI();
    }

    private void addImages() {
        mImages = new ArrayList<>();
        mImages.add(R.drawable.img_caurong);
        mImages.add(R.drawable.img_sunwheel);
        mImages.add(R.drawable.img_danang);
    }

    private void initUI() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewpager = (ViewPager) findViewById(R.id.viewPagerTab);
        setupViewPager(mViewpager);
        mTabLayout.setupWithViewPager(mViewpager);
        setupTabIcons(mTabLayout, 0, R.drawable.ic_games_black_24dp);
        setupTabIcons(mTabLayout, 1, R.drawable.ic_polymer_black_24dp);
        setupTabIcons(mTabLayout, 2, R.drawable.ic_thumb_up_black_24dp);
    }

    private void setupViewPager(ViewPager viewPager) {
//        addImages();
        /*ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mImages);
        viewPager.setAdapter(imagePagerAdapter);*/
        final FirstFragment firstFragment = new FirstFragment();
        final SecondFragment secondFragment = new SecondFragment();
        final ThirdFragment thirdFragment = new ThirdFragment();
//        firstFragment.setUserVisibleHint(false);
//        secondFragment.setUserVisibleHint(false);
//        thirdFragment.setUserVisibleHint(false);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        adapter.addFragment(firstFragment, "a");
        adapter.addFragment(secondFragment, "b");
        adapter.addFragment(thirdFragment, "c");
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        changeDurationViewpager(viewPager);
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        firstFragment.setUserVisibleHint(true);
                        secondFragment.setUserVisibleHint(false);
                        thirdFragment.setUserVisibleHint(false);
                        break;
                    case 1:
                        firstFragment.setUserVisibleHint(false);
                        secondFragment.setUserVisibleHint(true);
                        thirdFragment.setUserVisibleHint(false);
                        break;
                    default:
                        firstFragment.setUserVisibleHint(false);
                        secondFragment.setUserVisibleHint(false);
                        thirdFragment.setUserVisibleHint(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
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

    private void setupTabIcons(TabLayout tabLayout, int index, int image) {
        TextView tabView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
//        tabView.setText(TAB_ITEM[index]);
        tabView.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);
        tabLayout.getTabAt(index).setCustomView(tabView);
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
