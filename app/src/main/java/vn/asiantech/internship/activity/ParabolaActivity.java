package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.ui.draw.DrawParabolaView;

/**
 * Created by ducle on 11/07/2017.
 * ParabolaActivity contain custom view
 */
public class ParabolaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawParabolaView mDrawParabolaView = new DrawParabolaView(this);
        setContentView(mDrawParabolaView);
    }
}
