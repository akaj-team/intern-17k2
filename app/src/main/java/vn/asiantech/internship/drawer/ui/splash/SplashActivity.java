package vn.asiantech.internship.drawer.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.TestActivity;
import vn.asiantech.internship.drawer.ui.main.MainActivity;

/**
 * SplashActivity
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.btnBai8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.btnBai15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, TestActivity.class));
            }
        });
    }
}
