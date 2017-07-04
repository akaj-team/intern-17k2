package vn.asiantech.internship.day15.drawer.ui.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * FeedActivity javadoc
 */
public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new FeedFragment()).commit();
    }
}
