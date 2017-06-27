package vn.asiantech.internship.ui.tablayout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * ViewPagerActivity created by thanh thien
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPagerFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        replaceFragmentContent();
    }

    private void replaceFragmentContent() {
        mFragment = new ViewPagerFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.lnContent, mFragment);
        fragmentTransaction.commit();
    }

    public void setFloatActionButton(int visible) {
        mFragment.setFloatActionButton(visible);
    }
}
