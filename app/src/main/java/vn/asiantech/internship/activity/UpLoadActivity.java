package vn.asiantech.internship.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;
import vn.asiantech.internship.fragment.UpLoadFragment;

/**
 * Created by ducle on 06/07/2017.
 * UpLoadActivity contain an UpLoadFragment
 */
public class UpLoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpLoadFragment upLoadFragment = new UpLoadFragment();
        fragmentTransaction.replace(R.id.flContain, upLoadFragment);
        fragmentTransaction.commit();
    }
}
