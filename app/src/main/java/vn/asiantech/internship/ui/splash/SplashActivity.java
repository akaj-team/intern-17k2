package vn.asiantech.internship.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.exday15.JsonActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.exday13.ChatActivity;
import vn.asiantech.internship.exday16.ViewPagerActivity;
import vn.asiantech.internship.exday24.ParapolActivity;
import vn.asiantech.internship.exfragment.RecyclerViewActivity;
import vn.asiantech.internship.feed.FeedActivity;
import vn.asiantech.internship.note.NoteActivity;
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
        Button btnExday9 = (Button) findViewById(R.id.btnExday9);
        Button btnExday7 = (Button) findViewById(R.id.btnExDay7);
        Button btnExday11 = (Button) findViewById(R.id.btnExday11);
        Button btnExday13 = (Button) findViewById(R.id.btnExday13);
        Button btnExday15 = (Button) findViewById(R.id.btnExday15);
        Button btnExday16 = (Button) findViewById(R.id.btnExday16);
        Button btnExday24 = (Button) findViewById(R.id.btnExday24);

        btnDrawer.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
        btnExday9.setOnClickListener(this);
        btnExday7.setOnClickListener(this);
        btnExday11.setOnClickListener(this);
        btnExday13.setOnClickListener(this);
        btnExday15.setOnClickListener(this);
        btnExday16.setOnClickListener(this);
        btnExday24.setOnClickListener(this);
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
                mIntent = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExDay7:
                mIntent = new Intent(SplashActivity.this, vn.asiantech.internship.exday7.MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday11:
                mIntent = new Intent(SplashActivity.this, NoteActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday13:
                mIntent = new Intent(SplashActivity.this, ChatActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday15:
                mIntent = new Intent(SplashActivity.this, JsonActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday16:
                mIntent = new Intent(SplashActivity.this, ViewPagerActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btnExday24:
                mIntent = new Intent(SplashActivity.this, ParapolActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
