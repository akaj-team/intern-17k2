package vn.asiantech.internship.day7.ex2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;

public class MainActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstFragment = (FirstFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.fragmentFirst);
        mSecondFragment = (SecondFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.fragmentSecond);
        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondFragment.setOutput(mFirstFragment.getInput());
            }
        });
    }

    public String onClick() {
        return mFirstFragment.getInput();
    }
}
