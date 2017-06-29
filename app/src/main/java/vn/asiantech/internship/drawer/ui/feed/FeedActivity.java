package vn.asiantech.internship.drawer.ui.feed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
