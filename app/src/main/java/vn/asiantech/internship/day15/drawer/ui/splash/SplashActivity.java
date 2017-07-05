package vn.asiantech.internship.day15.drawer.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day13.ChatActivity;
import vn.asiantech.internship.day15.drawer.ui.main.MainActivity;
import vn.asiantech.internship.day16.ui.TabActivity;
import vn.asiantech.internship.day7.ui.CommunicateActivity;

/**
 * SplashActivity
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.btnBai7ex2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, CommunicateActivity.class));
            }
        });
        findViewById(R.id.btnBai15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
        findViewById(R.id.btnBai16).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, TabActivity.class));
            }
        });
        findViewById(R.id.btnBai13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, ChatActivity.class));
            }
        });
    }
}
