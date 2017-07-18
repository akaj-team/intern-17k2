package vn.asiantech.internship.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;

/**
 * Used to display fragment
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-13
 */
public class SendActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Button btnSend2 = (Button) findViewById(R.id.btnSend2);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFirstFragment = new FirstFragment();
        fragmentTransaction.replace(R.id.flContainer1, mFirstFragment);
        mSecondFragment = new SecondFragment();
        fragmentTransaction.replace(R.id.flContainer2, mSecondFragment);
        fragmentTransaction.commit();

        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSecondFragment.setResult(mFirstFragment.getDataInput());
            }
        });
    }

    public void onClick() {
        mSecondFragment.setResult(mFirstFragment.getDataInput());
    }
}
