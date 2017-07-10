package vn.asiantech.internship.ui.anotation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import vn.asiantech.internship.R;

/**
 * JsonActivity
 * Created by Thanh Thien
 */
@EActivity(R.layout.activity_contact_anotation)
public class ContactActivity extends AppCompatActivity {
    @ViewById(R.id.frameLayout)
    FrameLayout mFrameLayout;

    @AfterViews
    void afterViews() {
        replaceFragment();
    }

    private void replaceFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new ContactFragment_());
        fragmentTransaction.commit();
    }
}
