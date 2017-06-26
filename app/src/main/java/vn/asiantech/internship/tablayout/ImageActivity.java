package vn.asiantech.internship.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by sony on 26/06/2017.
 */

public class ImageActivity extends AppCompatActivity {
    private ViewPager mViewPagerImage;
    private TabLayout mTlImage;
    private List<Integer> mImages = new ArrayList<>();
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);
        mViewPagerImage = (ViewPager) findViewById(R.id.viewPagerTab);
        mViewPagerImage.setPageTransformer(true, new ZoomOutPageTransformer());
        mTlImage = (TabLayout) findViewById(R.id.tlImage);
        FragmentManager manager = getSupportFragmentManager();
        mImages.add(R.drawable.ic_six);
        mImages.add(R.drawable.ic_ten);
        mImages.add(R.drawable.ic_two);
        mAdapter = new ImageAdapter(manager, mImages);
        mViewPagerImage.setAdapter(mAdapter);
        mTlImage.setupWithViewPager(mViewPagerImage);
        mViewPagerImage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlImage));
        mTlImage.setTabsFromPagerAdapter(mAdapter);

    }
}
