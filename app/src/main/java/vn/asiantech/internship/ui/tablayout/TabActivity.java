package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.others.CustomTab;
import vn.asiantech.internship.ui.adapters.ViewPagerAdapter;

/**
 * Activity for tab layout.
 * Created by AnhHuy on 26-Jun-17.
 */
public class TabActivity extends AppCompatActivity {
    private final ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 5);
    private final String[] mTextTab = {"Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5"};
    private final int[] mIconTab = {R.drawable.selector_bubble, R.drawable.selector_cloud_queue,
            R.drawable.selector_nfc, R.drawable.selector_shopping, R.drawable.seletor_filter_vintage};

    private ViewPager mViewPagerTab;
    private TabLayout mTabLayoutTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initView();
        initViewPager();
        customTabLayout();
    }

    private void initView() {
        mViewPagerTab = (ViewPager) findViewById(R.id.viewPagerTab);
        mTabLayoutTab = (TabLayout) findViewById(R.id.tabLayout);
    }

    private void initViewPager() {
        mViewPagerTab.setAdapter(mPagerAdapter);
        mTabLayoutTab.setupWithViewPager(mViewPagerTab);
    }

    private void customTabLayout() {
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.text_view_tab, new LinearLayout(this), false);
            CustomTab customTab = (CustomTab) view.findViewById(R.id.customTab);
            if (i == 0) {
                customTab.setSelected(true);
            }
            TextView tvTabText = (TextView) view.findViewById(R.id.tvTabLayout);
            tvTabText.setText(mTextTab[i]);
            tvTabText.setTextColor(ContextCompat.getColorStateList(this, R.color.selector_text_view));
            tvTabText.setCompoundDrawablesWithIntrinsicBounds(0, mIconTab[i], 0, 0);
            TabLayout.Tab tab = mTabLayoutTab.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(view);
            }
            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerTab.setCurrentItem(position);
                }
            });
        }
    }
}

