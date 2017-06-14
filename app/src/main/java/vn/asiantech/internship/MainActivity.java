package vn.asiantech.internship;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.fragments.FirstFragment;
import vn.asiantech.internship.fragments.SecondFragment;

/**
 * Created by Hai on 6/13/2017.
 */
public class MainActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment(new SecondFragment.OnListener() {
            @Override
            public void onSendData() {
                mSecondFragment.setText(mFirstFragment.getText());
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frgContent1, mFirstFragment);
        transaction.replace(R.id.frgContent2, mSecondFragment);
        transaction.commit();
        Button btnSendData = (Button) findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondFragment.setText(mFirstFragment.getText());
            }
        });
    }
}
