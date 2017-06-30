package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.feed.FeedActivity;
import vn.asiantech.internship.friend.ListFriendActivity;
import vn.asiantech.internship.ui.main.ChatActivity;
import vn.asiantech.internship.ui.main.FootballStarActivity;
import vn.asiantech.internship.ui.main.MainActivity;
import vn.asiantech.internship.ui.main.NoteActivity;
import vn.asiantech.internship.ui.main.PassDataActivity;
import vn.asiantech.internship.ui.main.TestActivity;

/**
 * Created by PC on 6/15/2017.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button btnDay5 = (Button) findViewById(R.id.btnDay5);
        Button btnDay6 = (Button) findViewById(R.id.btnDay6);
        Button btnDay7 = (Button) findViewById(R.id.btnDay7);
        Button btnDay9 = (Button) findViewById(R.id.btnDay9);
        Button btnDay11 = (Button) findViewById(R.id.btnDay11);
        Button btnDay13 = (Button) findViewById(R.id.btnDay13);
        Button btnDay15 = (Button) findViewById(R.id.btnDay15);
        Button btnDay16 = (Button) findViewById(R.id.btnDay16);

        btnDay5.setOnClickListener(this);
        btnDay6.setOnClickListener(this);
        btnDay7.setOnClickListener(this);
        btnDay9.setOnClickListener(this);
        btnDay11.setOnClickListener(this);
        btnDay13.setOnClickListener(this);
        btnDay15.setOnClickListener(this);
        btnDay16.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDay5:
                Intent intent = new Intent(SplashActivity.this, ListFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDay6:
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnDay7:
                Intent intent2 = new Intent(SplashActivity.this, PassDataActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnDay9:
                Intent intent3 = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnDay11:
                Intent intent4 = new Intent(SplashActivity.this, NoteActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnDay13:
                Intent intent13 = new Intent(SplashActivity.this, ChatActivity.class);
                startActivity(intent13);
                break;
            case R.id.btnDay15:
                Intent intent15 = new Intent(SplashActivity.this, TestActivity.class);
                startActivity(intent15);
                break;
            case R.id.btnDay16:
                Intent intent16 = new Intent(SplashActivity.this, FootballStarActivity.class);
                startActivity(intent16);
        }
    }
}
