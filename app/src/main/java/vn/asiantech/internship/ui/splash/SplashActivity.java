package vn.asiantech.internship.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.exday19.MusicActivity;
import vn.asiantech.internship.exfragment.RecyclerViewActivity;
import vn.asiantech.internship.R;
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
        Button btnExday19 = (Button) findViewById(R.id.btnExday19);

        btnDrawer.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
        btnExday19.setOnClickListener(this);
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
            case R.id.btnExday19:
                mIntent = new Intent(SplashActivity.this, MusicActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
