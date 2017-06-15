package vn.asiantech.internship.friend.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_friend);
        getSupportFragmentManager().beginTransaction().replace(R.id.frContainer, new FriendFragment()).commit();
    }
}
