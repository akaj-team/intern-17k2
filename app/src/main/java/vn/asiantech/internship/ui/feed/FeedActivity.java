package vn.asiantech.internship.ui.feed;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class FeedActivity extends AppCompatActivity{
    private FeedFragment mFeedFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mFeedFragment=new FeedFragment();
        android.app.FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.flContent,mFeedFragment);
        transaction.commit();
    }
}
