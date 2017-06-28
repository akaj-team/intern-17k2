package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.feed.FeedActivity;
import vn.asiantech.internship.ui.fragments.SendDataActivity;
import vn.asiantech.internship.ui.chat.ChatActivity;
import vn.asiantech.internship.ui.friendlist.FriendActivity;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 *
 * Created by Hai on 6/15/2017.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intitView();
    }

    private void intitView() {
        Button btnDay5Ex1 = (Button) findViewById(R.id.btnDay5Ex1);
        Button btnDay6Ex1 = (Button) findViewById(R.id.btnDay6Ex1);
        Button btnDay11Ex1 = (Button) findViewById(R.id.btnDay11Ex2);
        Button btnFragment = (Button) findViewById(R.id.btnFragment);
        btnDay5Ex1.setOnClickListener(this);
        btnDay6Ex1.setOnClickListener(this);
        btnDay11Ex1.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
        Button btnDay13Chat = (Button) findViewById(R.id.btnDay13Ex1);
        btnDay5Ex1.setOnClickListener(this);
        btnDay6Ex1.setOnClickListener(this);
        btnDay13Chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDay5Ex1:
                Intent intentFriendActivity = new Intent(SplashActivity.this, FriendActivity.class);
                startActivity(intentFriendActivity);
                break;
            case R.id.btnDay6Ex1:
                Intent intentDrawerActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentDrawerActivity);
                break;
            case R.id.btnDay11Ex2:
                Intent intentDay11Ex2 = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(intentDay11Ex2);
                break;
            case R.id.btnFragment:
                Intent intentFragment = new Intent(SplashActivity.this, SendDataActivity.class);
                startActivity(intentFragment);
            case R.id.btnDay13Ex1:
                Intent intentChat = new Intent(SplashActivity.this, ChatActivity.class);
                startActivity(intentChat);
        }
    }
}
