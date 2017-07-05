package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.MainIntentActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.activity.ChatActivity;
import vn.asiantech.internship.activity.AnimalActivity;
import vn.asiantech.internship.activity.FriendActivity;
import vn.asiantech.internship.ui.feed.FeedActivity;
import vn.asiantech.internship.ui.main.MainActivity;
import vn.asiantech.internship.activity.TestActivity;
import vn.asiantech.internship.ui.main.NoteActivity;

/**
 * Created by ducle on 15/06/2017.
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnBai5;
    private Button mBtnBai6;
    private Button mBtnBai7;
    private Button mBtnBai9;
    private Button mBtnBai13;
    private Button mBtnBai15;
    private Button mBtnBai11;
    private Button mBtnBai16;

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
        mBtnBai9.setOnClickListener(this);
        mBtnBai13.setOnClickListener(this);
        mBtnBai15.setOnClickListener(this);
        mBtnBai11.setOnClickListener(this);
        mBtnBai16.setOnClickListener(this);
    }

    private void initViews() {
        mBtnBai5 = (Button) findViewById(R.id.btnBai5);
        mBtnBai6 = (Button) findViewById(R.id.btnBai6);
        mBtnBai7 = (Button) findViewById(R.id.btnBai7);
        mBtnBai9 = (Button) findViewById(R.id.btnBai9);
        mBtnBai13 = (Button) findViewById(R.id.btnBai13);
        mBtnBai15 = (Button) findViewById(R.id.btnBai15);
        mBtnBai11 = (Button) findViewById(R.id.btnBai11);
        mBtnBai16 = (Button) findViewById(R.id.btnBai16);
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
            case R.id.btnBai9:
                startActivity(new Intent(this, FeedActivity.class));
                break;
            case R.id.btnBai13:
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case R.id.btnBai15:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.btnBai11:
                startActivity(new Intent(this, NoteActivity.class));
                break;
            case R.id.btnBai16:
                startActivity(new Intent(this, AnimalActivity.class));
                break;
        }
    }
}
