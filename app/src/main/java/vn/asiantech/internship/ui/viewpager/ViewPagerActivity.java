package vn.asiantech.internship.ui.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eftimoff.viewpagertransformers.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.ViewPagerAdapter;

/**
 * Activity contain viewpager and tab layout
 * <p>
 * Created by Hai on 6/27/2017.
 */
public class ViewPagerActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPagerAdapter mAdapter;
    private CustomView mView;

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"Page 1", "Page 2", "Page 3", "Page 4"};
    private int[] mIconTitle = {R.drawable.custom_one, R.drawable.custom_two, R.drawable.custom_third, R.drawable.custom_fourth};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mView = new CustomView(this);
        createFragmentList();
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new AccordionTransformer());
        mTabLayout.setupWithViewPager(viewPager);
        setupLayout();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mView.setSelected(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mView.setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void setupLayout() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            TextView tvTabTitle = (TextView) view.findViewById(R.id.tvTabTitle);
            tvTabTitle.setText(mAdapter.getPageTitle(i));
            tvTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, mIconTitle[i], 0, 0);
            tvTabTitle.setGravity(Gravity.CENTER);
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
    }

    private void createFragmentList() {
        mFragments.add(new FirstFragment());
        mFragments.add(new SecondFragment());
        mFragments.add(new ThirdFragment());
        mFragments.add(new FourthFragment());
    }
}
