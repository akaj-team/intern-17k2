package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 28-06-2017.
 */
public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PageAFragment pageAFragment = new PageAFragment();
        transaction.replace(R.id.flViewPager, pageAFragment).commit();
    }
}
