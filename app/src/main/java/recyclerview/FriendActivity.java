package recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Use to contain fragment that had a recyclerView
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
    }
}
