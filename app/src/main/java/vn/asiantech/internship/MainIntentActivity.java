package vn.asiantech.internship;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import vn.asiantech.internship.fragments.FirstFragment;
import vn.asiantech.internship.fragments.SecondFragment;

/**
 * Created by Administrator on 6/13/2017.
 */
public class MainIntentActivity extends AppCompatActivity implements SecondFragment.OnClickButton {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    private FrameLayout mFlFrag1;
    private FrameLayout mFlFrag2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_day7);

        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
    }

    @Override
    public void setClick() {
        String input = mFirstFragment.getData();
        mSecondFragment.setData(input);
    }
}
