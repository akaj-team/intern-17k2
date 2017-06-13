package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BFragment.BFragmentInterface {

    private AFragment mAFragment;
    private BFragment mBFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        setFragment();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText();
            }
        });
    }

    private void setFragment() {
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
