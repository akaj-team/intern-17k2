package vn.asiantech.internship.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.exday13.ChatActivity;
import vn.asiantech.internship.exfragment.RecyclerViewActivity;
import vn.asiantech.internship.feed.ActivityFeed;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 15/06/2017.
 */
public class SplashActivity extends Activity implements View.OnClickListener {
    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnDrawer = (Button) findViewById(R.id.btnDrawer);
        Button btnFragment = (Button) findViewById(R.id.btnFragment);
        Button btnFeed = (Button) findViewById(R.id.btnExday9);
        Button btnExday7 = (Button) findViewById(R.id.btnExDay7);
        Button btnExday13 = (Button) findViewById(R.id.btnExday13);

        btnDrawer.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
        btnFeed.setOnClickListener(this);
        btnExday7.setOnClickListener(this);
        btnExday13.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDrawer:
                mIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnFragment:
                mIntent = new Intent(SplashActivity.this, RecyclerViewActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday9:
                mIntent = new Intent(SplashActivity.this, ActivityFeed.class);
                startActivity(mIntent);
                break;
            case R.id.btnExDay7:
                mIntent = new Intent(SplashActivity.this, vn.asiantech.internship.exday7.MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday13:
                mIntent = new Intent(SplashActivity.this, ChatActivity.class);
                startActivity(mIntent);
        }
    }
}
