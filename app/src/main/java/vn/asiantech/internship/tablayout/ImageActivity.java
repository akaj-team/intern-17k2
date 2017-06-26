package vn.asiantech.internship.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        //  mViewPagerImage.setPageTransformer(true, new DepthPageTransformer());
       // Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
      //  mViewPagerImage.startAnimation(animation1);
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
        mViewPagerImage.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();


                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(1);

                } else if (position <= 1) { // [-1,1]

                    page.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(1);
                }
            }
        });
    }
}
