package vn.asiantech.internship.drawer.day16.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.ImagePagerAdapter;

public class TabActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
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
        mImages.add(R.drawable.img_biendanang);
        mImages.add(R.drawable.img_binhdinh);
    }

    private void initUI() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerTab);
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mImages);
        tabLayout.setupWithViewPager(viewPager);
    }
}
