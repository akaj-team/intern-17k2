package vn.asiantech.internship.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display viewpager on activity.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
public class ImageActivity extends AppCompatActivity {
    private final List<Integer> mImages = Arrays.asList(R.drawable.ic_one, R.drawable.ic_two, R.drawable.ic_three, R.drawable.ic_four, R.drawable.ic_five);
    private final List<Integer> mIcons = Arrays.asList(R.drawable.ic_search_amber_900_36dp, R.drawable.ic_do_not_disturb_on_amber_900_36dp, R.drawable.ic_home_amber_900_36dp, R.drawable.ic_free_breakfast_amber_900_36dp, R.drawable.ic_person_pin_amber_900_36dp);
    private final List<CustomTabLayout> mViewTabs = new ArrayList<>();
    private final List<TextView> mTvTitles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ViewPager bigViewPager = (ViewPager) findViewById(R.id.bigViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlImage);
        FragmentManager manager = getSupportFragmentManager();
        BigAdapter adapter = new BigAdapter(manager, mImages);
        bigViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(bigViewPager);
        bigViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        for (int i = 0; i < adapter.getCount(); i++) {
            RelativeLayout rlTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            TextView tvTitleTab = (TextView) rlTab.findViewById(R.id.tvTitleTab);
            CustomTabLayout customTab = (CustomTabLayout) rlTab.findViewById(R.id.viewCustomTab);
            mTvTitles.add(tvTitleTab);
            mViewTabs.add(customTab);
            tvTitleTab.setText(adapter.getPageTitle(i));
            tvTitleTab.setCompoundDrawablesWithIntrinsicBounds(0, mIcons.get(i), 0, 0);
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(rlTab);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewTabs.get(tab.getPosition()).setSelected(true);
                mTvTitles.get(tab.getPosition()).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRed));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewTabs.get(tab.getPosition()).setSelected(false);
                mTvTitles.get(tab.getPosition()).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreyDark));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
