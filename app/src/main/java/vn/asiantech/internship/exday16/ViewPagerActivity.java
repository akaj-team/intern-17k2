package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */
public class ViewPagerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        transaction.replace(R.id.fragmentViewPager, viewPagerFragment).commit();
    }
}
