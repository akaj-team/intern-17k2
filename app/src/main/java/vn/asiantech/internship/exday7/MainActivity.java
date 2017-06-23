package vn.asiantech.internship.exday7;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Javadoc
 * Created by datbu on 14-06-2017.
 */
public class MainActivity extends AppCompatActivity {
    public static final String KEY = "Data";

    private TopFragment mTopFragment;
    private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        Button btnPassData = (Button) findViewById(R.id.btnPassData);
        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomFragment.setText(mTopFragment.getText());
            }
        });

        mTopFragment = new TopFragment();
        mBottomFragment = new BottomFragment();
        SendData myData = new SendData(new OnSendDataListener() {
            @Override
            public void onSendDataClick(TextView tv) {
                tv.setText(mTopFragment.getText());
            }
        });
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, myData);
        mBottomFragment.setArguments(bundle);
        replaceFragment(mTopFragment, R.id.flContentPass, false);
        replaceFragment(mBottomFragment, R.id.flContentTwo, false);
    }

    public void replaceFragment(Fragment fragment, int idContent, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(idContent, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.commit();
    }

    /**
     * Javadoc
     * Created by datbu on 14-06-2017.
     */
    interface OnSendDataListener {
        void onSendDataClick(TextView tv);
    }
}
