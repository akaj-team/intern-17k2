package vn.asiantech.internship.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

public class CommunicateActivity extends AppCompatActivity {

    private InputFragment mInputFragment;
    private ActionFragment mActionFragment;

    private String mContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initUi();
    }

    private void initUi() {
        mInputFragment = new InputFragment();
        mActionFragment = new ActionFragment(new ActionFragment.SendData() {
            @Override
            public void onSend() {
                mContent = mInputFragment.getText();
                mActionFragment.setText(mContent);
            }
        });
        addFragment(R.id.frContainerInput, mInputFragment);
        addFragment(R.id.frContainerAction, mActionFragment);
    }

    private void addFragment(int container, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}
