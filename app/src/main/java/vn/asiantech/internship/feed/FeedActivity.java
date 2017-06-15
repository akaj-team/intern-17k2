package vn.asiantech.internship.feed;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Created by sony on 15/06/2017.
 */

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FeedFragment feedFragment = new FeedFragment();
        fragmentTransaction.replace(R.id.frContainer, feedFragment);
        fragmentTransaction.commit();
    }
}
