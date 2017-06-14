package vn.asiantech.internship.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragments.InputFragment;
import vn.asiantech.internship.fragments.ResultFragment;

/**
 *
 * Created by Hai on 6/13/2017.
 */
public class MainActivity extends AppCompatActivity {
    private InputFragment mInputFragment;
    private ResultFragment mResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputFragment = new InputFragment();
        mResultFragment = new ResultFragment(new ResultFragment.OnListener() {
            @Override
            public void onSendData() {
                mResultFragment.setText(mInputFragment.getText());
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frgContent1, mInputFragment);
        transaction.replace(R.id.frgContent2, mResultFragment);
        transaction.commit();
        Button btnSendData = (Button) findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultFragment.setText(mInputFragment.getText());
            }
        });
    }
}
