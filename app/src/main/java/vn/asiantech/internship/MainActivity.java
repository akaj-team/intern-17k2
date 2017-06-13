package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TopFragment mTopFragment;
    private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPassData = (Button) findViewById(R.id.btnPassData);
        btnPassData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomFragment.setText(mTopFragment.getText());
            }
        });

        mTopFragment = new TopFragment();
        mBottomFragment = new BottomFragment();
        SendData myData = new SendData(new OnClick() {
            @Override
            public void onClick(TextView tv) {
                tv.setText(mTopFragment.getText());
            }
        });
        Bundle bundle = new Bundle();
        bundle.putSerializable("Data", myData);
        mBottomFragment.setArguments(bundle);
        addFragment(mTopFragment, R.id.frgContent1, false);
        addFragment(mBottomFragment, R.id.frgContent2, false);
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

