package vn.asiantech.internship.day24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 07/07/2017.
 */

public class MyChartDraw extends View {
    private Paint mPaint1;
    private Paint mPaint2;

    public MyChartDraw(Context context) {
        this(context, null);
    }

    public MyChartDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint1();
        initPaint2();
    }

    public MyChartDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Create Coordinate axis
    }

    private void initPaint1(){
        mPaint1=new Paint();
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(3);
        mPaint1.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void initPaint2(){
        mPaint2=new Paint();
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(2);
        mPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.RED);
    }
}
