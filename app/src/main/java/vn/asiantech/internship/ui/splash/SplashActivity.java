package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Created by anhhuy on 15/06/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private Button mBtnBai5;
    private Button mBtnBai6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSplashView();
        onClickButton();
    }

    private void initSplashView(){
        mBtnBai5 = (Button) findViewById(R.id.btnBai5);
        mBtnBai6 = (Button) findViewById(R.id.btnBai6);
    }

    private void onClickButton(){
        mBtnBai6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBai6 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intentBai6);
            }
        });
    }
}
