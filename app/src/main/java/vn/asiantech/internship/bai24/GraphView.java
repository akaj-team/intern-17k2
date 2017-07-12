package vn.asiantech.internship.bai24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.ui.CustomView;

/**
 * Created by at-dinhvo on 07/07/2017.
 */
public class GraphView extends CustomView {
    private static final String TAG = "at-dinhvo";
    private Paint mPaint;
    private int mDistance = 30;
    private int mRatio = 30;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPivotX(canvas);
        drawPivotY(canvas);
        drawGraph(canvas);
    }

    private void drawGraph(Canvas canvas) {
        mPaint.setStrokeWidth(2);
        for (int i = 0; i < getWidth() / 2; i++) {
            drawPoint(canvas, i);
        }
    }

    private void drawPoint(Canvas canvas, int x) {
        mPaint.setStrokeWidth(10);
        float X = x / mDistance;
        float Y1 = (3 * X * X - 4 * X + 1);
        float Y = (getHeight() / 2 - Y1) * mDistance;
        canvas.drawCircle(X + getWidth() / 2, Y, mDistance, mPaint);
        Log.e(TAG, "drawPoint: X = " + (X + getWidth()/2) + ", Y = " + Y);
        canvas.drawPoint(X, Y, mPaint);
    }

    private void drawPivotX(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(getWidth() / 2, mDistance, getWidth() / 2, getHeight() - mDistance, mPaint);
        mPaint.setStrokeWidth(5);
        for (float i = ((-getWidth() + mDistance) / 2) / mRatio; i <= ((getWidth() - mDistance) / 2) / mRatio; i++) {
            canvas.drawPoint((getWidth() / 2 + i * mRatio), getHeight() / 2, mPaint);
        }
    }

    private void drawPivotY(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(mDistance, getHeight() / 2, getWidth() - mDistance, getHeight() / 2, mPaint);
        mPaint.setStrokeWidth(6);
        for (float i = (-getHeight() / 2) / mRatio; i <= (getHeight() / 2) / mRatio; i++) {
            canvas.drawPoint(getWidth() / 2, (getHeight() / 2 + i * mRatio), mPaint);
        }
    }
}
