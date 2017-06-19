package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.friendlist.FriendActivity;
import vn.asiantech.internship.ui.main.MainActivity;

/**
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
        btnDay5Ex1.setOnClickListener(this);
        btnDay6Ex1.setOnClickListener(this);
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
        }
    }
}
