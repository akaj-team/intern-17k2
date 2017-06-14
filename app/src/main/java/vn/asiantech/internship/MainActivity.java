package vn.asiantech.internship;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.fragments.FirstFragment;
import vn.asiantech.internship.fragments.SecondFragment;
import vn.asiantech.internship.models.MyData;


/**
 * This class create by VanCuong on 06/13/2017
 */
public class MainActivity extends AppCompatActivity {
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPassData = (Button) findViewById(R.id.btnPassData);
        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSecondFragment.setText(mFirstFragment.getText());
            }
        });

        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
        MyData myData = new MyData(new OnClick() {
            @Override
            public void onClick(TextView tv) {
                tv.setText(mFirstFragment.getText());
            }
        });
        Bundle bundle = new Bundle();
        bundle.putSerializable("Data", myData);
        mSecondFragment.setArguments(bundle);
        addFragment(mFirstFragment, R.id.frgContent1, false);
        addFragment(mSecondFragment, R.id.frgContent2, false);
    }

    public void addFragment(Fragment fragment, int idContaint, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(idContaint, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getTag());
        }
        transaction.commit();
    }

    public interface OnClick {
        void onClick(TextView tv);
    }
}
