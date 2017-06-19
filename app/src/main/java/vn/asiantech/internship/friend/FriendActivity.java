package vn.asiantech.internship.friend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.asiantech.internship.R;

public class FriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new FriendFragment()).commit();
    }
}
