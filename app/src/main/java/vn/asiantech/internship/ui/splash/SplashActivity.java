package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import recyclerview.FriendActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Used to display button to choose exercise
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-15
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button btnNgay5 = (Button) findViewById(R.id.btnNgay5);
        Button btnNgay6 = (Button) findViewById(R.id.btnNgay6);
        Button btnNgay7 = (Button) findViewById(R.id.btnNgay7);
        btnNgay5.setOnClickListener(this);
        btnNgay6.setOnClickListener(this);
        btnNgay7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNgay5:
                startActivity(new Intent(this, FriendActivity.class));
                break;
            case R.id.btnNgay6:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnNgay7:
                break;
        }
    }
}
