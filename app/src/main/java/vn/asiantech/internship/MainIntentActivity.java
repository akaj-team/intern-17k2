package vn.asiantech.internship;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.fragment.FirstIntentFragment;
import vn.asiantech.internship.fragment.SecondIntentFragment;

/**
 * MainActivity content 2 fragments
 */
public class MainIntentActivity extends AppCompatActivity implements SecondIntentFragment.OnClickButton {
    private FirstIntentFragment mFirstIntentFragment;
    private SecondIntentFragment mSecondIntentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent);
        Button btnActivity = (Button) findViewById(R.id.btnActivity);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mFirstIntentFragment.getData();
                mSecondIntentFragment.setData(input);
            }
        });
        mFirstIntentFragment = new FirstIntentFragment();
        mSecondIntentFragment = new SecondIntentFragment();
        switchFragment(mFirstIntentFragment, true, R.id.flFrag1);
        switchFragment(mSecondIntentFragment, true, R.id.flFrag2);
    }

    @Override
    public void setClick() {
        String input = mFirstIntentFragment.getData();
        mSecondIntentFragment.setData(input);
    }

    /**
     * switch fragment
     *
     * @param fragment       is fragment need add
     * @param addToBackStack is set add to back stack
     * @param id             is id of fragment
     */
    public void switchFragment(Fragment fragment, boolean addToBackStack, int id) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        if (fragment.getTag() == null) {
            ft.replace(id, fragment, fragment.toString());
        } else
            ft.replace(id, fragment);
        ft.commit();
    }
}
