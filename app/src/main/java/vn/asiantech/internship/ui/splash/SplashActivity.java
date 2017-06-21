package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import recyclerView.FriendActivity;
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
        Button btnDay5 = (Button) findViewById(R.id.btnDay5);
        Button btnDay6 = (Button) findViewById(R.id.btnDay6);
        Button btnDay7 = (Button) findViewById(R.id.btnDay7);
        btnDay5.setOnClickListener(this);
        btnDay6.setOnClickListener(this);
        btnDay7.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDay5:
                startActivity(new Intent(this, FriendActivity.class));
                break;
            case R.id.btnDay6:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnDay7:
                break;
        }
    }
}
