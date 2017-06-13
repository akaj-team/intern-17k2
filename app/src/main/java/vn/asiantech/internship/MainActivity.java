package vn.asiantech.internship;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Used to display fragment
 */
public class MainActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSecondSend = (Button) findViewById(R.id.btnSecondSend);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mFirstFragment = new FirstFragment();
        fragmentTransaction.replace(R.id.container1, mFirstFragment);
        mSecondFragment = new SecondFragment();
        fragmentTransaction.replace(R.id.container2, mSecondFragment);
        fragmentTransaction.commit();

        btnSecondSend.setOnClickListener(new View.OnClickListener() {
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
