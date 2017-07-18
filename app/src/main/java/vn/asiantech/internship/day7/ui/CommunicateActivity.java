package vn.asiantech.internship.day7.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * CommunicateActivity
 */
public class CommunicateActivity extends AppCompatActivity {

    private InputFragment mInputFragment;
    private ActionFragment mActionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initUi();
    }

    private void initUi() {
        mInputFragment = new InputFragment();
        mActionFragment = new ActionFragment();
        ActionFragment.OnSendDataListener onSendDataListener = new ActionFragment.OnSendDataListener() {
            @Override
            public void onSendData() {
                String mContent = mInputFragment.getText();
                mActionFragment.setText(mContent);
            }
        };
        mActionFragment.setOnSendDataListener(onSendDataListener);
        replaceFragment(R.id.frContainerInput, mInputFragment);
        replaceFragment(R.id.frContainerAction, mActionFragment);
    }

    private void replaceFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
