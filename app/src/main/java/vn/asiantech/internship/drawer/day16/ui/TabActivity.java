package vn.asiantech.internship.drawer.day16.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.PageAdapter;

import static vn.asiantech.internship.R.id.tabLayout;

/**
 * TabActivity
 */
public class TabActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private List<CustomView> mCustomTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initUI();
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerTab);
        mTabLayout = (TabLayout) findViewById(tabLayout);
        setupViewpager(viewPager);
        mTabLayout.setupWithViewPager(viewPager);
        mCustomTabs = new ArrayList<>();
        setupTabLayout("Search", 0, R.drawable.ic_loyalty_white_24dp);
        setupTabLayout("Place", 1, R.drawable.ic_motorcycle_white_24dp);
        setupTabLayout("Profile", 2, R.drawable.ic_rowing_white_24dp);
        setupTabLayout("Game", 3, R.drawable.ic_settings_input_antenna_white_24dp);
        setupTabLayout("Setting", 4, R.drawable.ic_open_with_white_24dp);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCustomTabs.get(tab.getPosition()).setSelected(true);
                mCustomTabs.get(tab.getPosition()).setBackgroundColor(getResources().getColor(R.color.colorTab));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mCustomTabs.get(tab.getPosition()).setSelected(false);
                mCustomTabs.get(tab.getPosition()).setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabLayout(String name, int index, int image) {
        View tabItem = LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
        TextView tvItem = (TextView) tabItem.findViewById(R.id.tvTab);
        CustomView customTabItem = (CustomView) tabItem.findViewById(R.id.customView);
        mCustomTabs.add(customTabItem);
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
                    .setDuration(600)
                    .start();
        }
    }

    private void setupViewpager(ViewPager viewPager) {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        initFragmentList(adapter);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCustomTabs.get(position).setSelected(true);
                mCustomTabs.get(position).setBackgroundColor(getResources().getColor(R.color.colorTab));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initFragmentList(PageAdapter adapter) {
        adapter.addFragment(new FirstFragment());
        adapter.addFragment(new SecondFragment());
        adapter.addFragment(new ThirdFragment());
        adapter.addFragment(new FourthFragment());
        adapter.addFragment(new FifthFragment());
    }
}
