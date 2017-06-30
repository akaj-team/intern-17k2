package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
public class PageAFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String mImage;
    private static String[] mTextTabs = {"Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5"};
    private static int[] mTabIconsSelecteds = {
            R.drawable.bg_tab_a,
            R.drawable.bg_tab_b,
            R.drawable.bg_tab_c,
            R.drawable.bg_tab_d,
            R.drawable.bg_tab_e
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_a, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), mImage);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        customTabView();
        return view;
    }

    private void customTabView() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View a = LayoutInflater.from(getContext()).inflate(R.layout.tablayout, new LinearLayout(getContext()), false);
            CustomTab customTab = (CustomTab) a.findViewById(R.id.myView);
            if (i == 1) {
                customTab.setSelected(true);
            }
            TextView tvTabText = (TextView) a.findViewById(R.id.tvTabTitle);
            tvTabText.setText(mTextTabs[i]);
            tvTabText.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.bg_tab_textview));
            tvTabText.setCompoundDrawablesWithIntrinsicBounds(0, mTabIconsSelecteds[i], 0, 0);
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(a);
            }
            final int position = i;
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(position);
                }
            });
        }
    }
}
