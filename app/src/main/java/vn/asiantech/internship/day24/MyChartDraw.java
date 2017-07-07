package vn.asiantech.internship.day24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 07/07/2017.
 */

public class MyChartDraw extends View {
    private static final int MARGIN_LEFT=80;
    private static final int MARGIN_RIGHT=50;
    private static final int MARGIN_TOP=50;

    private Paint mPaint1;
    private Paint mPaint2;
    private Path mPath1;
    private Path mPath2;

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
        canvas.drawLine(MARGIN_LEFT-50,getHeight()*3/4,getWidth()-MARGIN_RIGHT,getHeight()*3/4,mPaint1);
        if(mPath1==null){
            mPath1=new Path();
        }
        mPath1.moveTo(getWidth()-MARGIN_RIGHT-20,getHeight()*3/4+20);
        mPath1.lineTo(getWidth()-MARGIN_RIGHT+20,getHeight()*3/4+20);
        mPath1.lineTo(getWidth()-MARGIN_RIGHT,getHeight()*3/4);

        canvas.drawPath(mPath1,mPaint1);

        canvas.drawLine(getWidth()/2,MARGIN_TOP,getWidth()/2,getHeight()*3/4+MARGIN_TOP,mPaint1);

    }

    private void initPaint1(){
        mPaint1=new Paint();
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
