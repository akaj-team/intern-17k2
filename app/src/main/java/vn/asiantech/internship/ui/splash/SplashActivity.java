package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.MainIntentActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.activity.FriendActivity;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Created by ducle on 15/06/2017.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnBai5;
    private Button mBtnBai6;
    private Button mBtnBai7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_splash);
        initViews();
        setClickButton();
    }

    private void setClickButton() {
        mBtnBai5.setOnClickListener(this);
        mBtnBai6.setOnClickListener(this);
        mBtnBai7.setOnClickListener(this);
    }

    private void initViews() {
        mBtnBai5 = (Button) findViewById(R.id.btnBai5);
        mBtnBai6 = (Button) findViewById(R.id.btnBai6);
        mBtnBai7 = (Button) findViewById(R.id.btnBai7);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBai5:
                startActivity(new Intent(this, FriendActivity.class));
                break;
            case R.id.btnBai6:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnBai7:
                startActivity(new Intent(this, MainIntentActivity.class));
                break;
        }
    }
}
