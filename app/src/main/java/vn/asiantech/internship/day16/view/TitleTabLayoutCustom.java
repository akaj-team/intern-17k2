package vn.asiantech.internship.day16.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.adapter.ViewPagerOutAdapter;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class TitleTabLayoutCustom extends ViewGroup {
    private Paint mPaint;
    private int mPosition=2;

    public TitleTabLayoutCustom(Context context) {
        super(context);
        initPaint();
        setWillNotDraw(false);
    }

    public TitleTabLayoutCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleTabLayoutCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("mmmmmm", "onDraw: "+mPosition);
        RectF rectf = new RectF(mPosition * getWidth() / ViewPagerOutAdapter.SIZE, 0, (mPosition + 1) * getWidth() / ViewPagerOutAdapter.SIZE, getHeight());
        canvas.drawArc(rectf, 0, 180, false, mPaint);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.imageview_custom_tablayout_title));
        mPaint.setStrokeWidth(30);
    }

    public void setVT(int vt) {
        mPosition = vt;
        Log.d("mmmmmm", "setVT: "+mPosition);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
