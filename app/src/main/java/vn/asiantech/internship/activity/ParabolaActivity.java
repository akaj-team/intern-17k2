package vn.asiantech.internship.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import vn.asiantech.internship.ui.draw.DrawParabolaView;

/**
 * Created by ducle on 11/07/2017.
 * ParabolaActivity contain custom view
 */
public class ParabolaActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = ParabolaActivity.class.getSimpleName();
    private DrawParabolaView mDrawParabolaView;
    private float mHoldX;
    private float mHoldY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawParabolaView = new DrawParabolaView(this);
        mDrawParabolaView.setOnTouchListener(this);
        setContentView(mDrawParabolaView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHoldX = event.getX();
                mHoldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawParabolaView.move(event.getX() - mHoldX, event.getY() - mHoldY);
                mHoldX = event.getX();
                mHoldY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
