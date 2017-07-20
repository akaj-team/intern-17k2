package vn.asiantech.internship.day16.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class TitleTabLayoutCustom extends View {
    private Paint mPaint1;
    private Paint mPaint2;
    private boolean mIsCheck;

    public TitleTabLayoutCustom(Context context) {
        this(context, null);
    }

    public TitleTabLayoutCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint1();
        initPaint2();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsCheck) {
            RectF rectf = new RectF(0, -getHeight() / 2, getWidth(), getHeight() / 3);
            canvas.drawArc(rectf, 0, 180, false, mPaint1);
            RectF rect = new RectF(0, -getHeight() / 2, getWidth(), getHeight() / 3);
            canvas.drawArc(rect, 0, 180, false, mPaint2);
        } else {
            canvas.drawLine(0, 0, getWidth(), 0, mPaint2);
        }
    }

    private void initPaint1() {
        mPaint1 = new Paint();
        mPaint1.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(ContextCompat.getColor(getContext(), R.color.colorItem));

    }

    private void initPaint2() {
        mPaint2 = new Paint();
        mPaint2.setColor(ContextCompat.getColor(getContext(), R.color.stroke));
        mPaint2.setStrokeWidth(2);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void setSelected(boolean selected) {
        mIsCheck = selected;
        invalidate();
    }

    @Override
    public boolean isSelected() {
        return mIsCheck;
    }
}
