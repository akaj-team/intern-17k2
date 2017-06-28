package vn.asiantech.internship.day16.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.adapter.ViewPagerOutAdapter;
import vn.asiantech.internship.day16.view.TitleTabLayoutCustom;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class TabActivity extends AppCompatActivity {
    ViewPagerOutAdapter mViewPagerOutAdapter;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    private List<TitleTabLayoutCustom> mTabs = new ArrayList<>();
    private int[] mImages = {
            R.mipmap.ic_bicycle,
            R.mipmap.ic_animal,
            R.mipmap.ic_food,
            R.mipmap.ic_music,
            R.mipmap.ic_lead
    };
    private String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPagerOut);
        mTitles = getApplicationContext().getResources().getStringArray(R.array.tabtitles);
        mViewPagerOutAdapter = new ViewPagerOutAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerOutAdapter);
        mViewPagerOutAdapter.notifyDataSetChanged();
        mTabLayout.setupWithViewPager(mViewPager);
        final TitleTabLayoutCustom titleTabLayoutCustom = new TitleTabLayoutCustom(this);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                titleTabLayoutCustom.setVT(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        customTablayout();
    }

    public void customTablayout() {
        for (int i = 0; i < mViewPagerOutAdapter.getCount(); i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
            TextView tvTab = (TextView) v.findViewById(R.id.tvTabItem);
            tvTab.setText(mTitles[i]);
            tvTab.setCompoundDrawablesWithIntrinsicBounds(0, mImages[i], 0, 0);
            mTabLayout.getTabAt(i).setCustomView(v);
        }
    }

}
