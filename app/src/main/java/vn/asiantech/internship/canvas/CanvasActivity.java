package vn.asiantech.internship.canvas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 06/07/2017.
 */
public class CanvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }
}
