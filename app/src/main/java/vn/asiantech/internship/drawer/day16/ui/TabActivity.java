package vn.asiantech.internship.drawer.day16.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day16.adapter.ImagePagerAdapter;

public class TabActivity extends AppCompatActivity {

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
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerTab);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
    }

    private void setupViewPager(ViewPager viewPager) {
        addImages();
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(mImages);
        viewPager.setAdapter(imagePagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private void setupTabIcons(TabLayout tabLayout) {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
        tabOne.setText("Image1");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_games_black_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
        tabTwo.setText("Image2");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_polymer_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
        tabThree.setText("Image3");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_thumb_up_black_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }
}
