package vn.asiantech.internship.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import vn.asiantech.internship.R;

/**
 * Created by AnhHuy on 23-Jun-17.
 */

public class DemoActivity extends AppCompatActivity {
    private TextView mTvDemo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mTvDemo = (TextView) findViewById(R.id.tvDemo);

        try {
            InputStream inputStream = getAssets().open("question.json");
            int size = inputStream.available();
            byte[] buff = new byte[size];
            inputStream.read(buff);
            inputStream.close();
            String s = new String(buff);
            mTvDemo.setText(s);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
