package vn.asiantech.internship.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.fragments.InputFragment;
import vn.asiantech.internship.ui.fragments.ResultFragment;

/**
 * activity send data
 * <p>
 * Created by Hai on 6/13/2017.
 */
public class SendDataActivity extends AppCompatActivity implements ResultFragment.OnClickListener {
    private InputFragment mInputFragment;
    private ResultFragment mResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_data);
        mInputFragment = new InputFragment();
        mResultFragment = new ResultFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flInput, mInputFragment);
        transaction.replace(R.id.flResult, mResultFragment);
        transaction.commit();
        Button btnSendData = (Button) findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText();
            }
        });
    }

    @Override
    public void onSendData() {
        setText();
    }

    private void setText() {
        mResultFragment.setText(mInputFragment.getText());
    }
}
