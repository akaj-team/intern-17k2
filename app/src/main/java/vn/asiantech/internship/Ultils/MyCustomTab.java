package vn.asiantech.internship.Ultils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by PC on 6/27/2017.
 */

public class MyCustomTab extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public MyCustomTab(Context context) {
        super(context);
        Log.i("tag11", "con1");
    }

    public MyCustomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i("tag11", "con2");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("tag11", "onDraw");
        mWidth = getWidth();
        mHeight = getHeight();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        Point o = new Point(mWidth / 2, mHeight * 3);
        Log.i("tag11", getWidth() + "---" + getHeight());
        canvas.drawCircle(getWidth() / 2, getWidth()/2 + 10, getWidth()/2, mPaint);
    }
}
