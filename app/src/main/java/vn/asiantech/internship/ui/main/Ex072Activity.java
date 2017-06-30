package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragment.AFragment;
import vn.asiantech.internship.ui.fragment.BFragment;

/**
 * Avtivity of Ex 7 .2
 */
public class Ex072Activity extends AppCompatActivity implements BFragment.OnBFragmentListener {

    private AFragment mAFragment;
    private BFragment mBFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex072);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        replaceFragment();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText();
            }
        });
    }

    private void replaceFragment() {
        mAFragment = new AFragment();
        mBFragment = new BFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentA, mAFragment);
        fragmentTransaction.replace(R.id.fragmentB, mBFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void setText() {
        mBFragment.setText(mAFragment.getText());
    }
}
