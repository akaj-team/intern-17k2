package vn.asiantech.internship.bai24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import vn.asiantech.internship.day16.ui.CustomView;

/**
 * Created by at-dinhvo on 07/07/2017.
 */
public class GraphView extends CustomView {
    private static final String TAG = "at-dinhvo";
    private Paint mPaint;
    private int mDistance = 40;
    private int mRatio = 30;
    private Point mNewPosition;
    private Point mRootPoint;
    private boolean isDrawed;

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
        Log.e(TAG, "initPaint: bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mRootPoint = new Point();
        mNewPosition = new Point();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDrawed) {
            mRootPoint.x = getWidth() / 2;
            mRootPoint.y = getHeight() / 2;
            isDrawed = true;
        }
        drawPivot(canvas);
        drawGrid(canvas);
        drawGraph(canvas);
        Log.e(TAG, "onDraw: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + mRootPoint.x);
    }

    private void drawGraph(Canvas canvas) {
        mPaint.setStrokeWidth(4);
        Point mOldPoint = getGraphPoint(getWidth() / 3 - 0.1f);
        Point mNewPoint;
        for (float i = getWidth() / 3; i < 2 * getWidth() / 3; i += 0.1) {
            mNewPoint = getGraphPoint(i);
            canvas.drawLine(mOldPoint.x, mOldPoint.y, mNewPoint.x, mNewPoint.y, mPaint);
            mOldPoint = mNewPoint;
        }
    }

    // TODO: 7/17/17 decrease loop
    private Point getGraphPoint(float x) {
        Point point = new Point();
        float X = (x - getWidth() / 2) / mRatio;
        float Y = (3 * X * X - 4 * X + 1);
        float y = (getHeight() / 2 - Y * mRatio);
        point.x = (int) x;
        point.y = (int) y;
        return point;
    }

    private void drawPivot(Canvas canvas) {
        mPaint.setStrokeWidth(4);
        canvas.drawLine(mRootPoint.x, mDistance, mRootPoint.x, getHeight() - mDistance, mPaint);
        canvas.drawLine(mDistance, mRootPoint.y, getWidth() - mDistance, mRootPoint.y, mPaint);
        mPaint.setStrokeWidth(5);
        for (float i = (-mRootPoint.x + mDistance) / mRatio; i <= (mRootPoint.y - mDistance) / mRatio; i++) {
            canvas.drawPoint((mRootPoint.x + i * mRatio), mRootPoint.y, mPaint);
        }
        for (float i = (-mRootPoint.y) / mRatio; i <= (mRootPoint.y) / mRatio; i++) {
            canvas.drawPoint(mRootPoint.x, (mRootPoint.y + i * mRatio), mPaint);
        }
    }

    private void drawGrid(Canvas canvas) {
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.DKGRAY);
        for (float i = (-mRootPoint.x + mDistance) / mRatio; i <= (mRootPoint.y - mDistance) / mRatio; i++) {
            canvas.drawLine((mRootPoint.x + i * mRatio), mDistance,
                    (mRootPoint.x + i * mRatio), getHeight() - mDistance, mPaint);
            canvas.drawText(String.valueOf(i), getHeight() - 5, mRootPoint.x + i * mRatio, mPaint);
        }
        for (float i = (-mRootPoint.y) / mRatio; i <= (mRootPoint.y) / mRatio; i++) {
            if (i > 100) {
                mDistance += 5;
            }
            canvas.drawLine(mDistance, mRootPoint.y + i * mRatio,
                    getWidth(), (mRootPoint.y + i * mRatio), mPaint);
            canvas.drawText(String.valueOf((int) i), 5, mRootPoint.y + i * mRatio, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                Log.e(TAG, "onTouch_UP: X0 = " + x + ", Y0 = " + y);
                break;
            case MotionEvent.ACTION_DOWN:
                mNewPosition.x = (int) event.getX();
                mNewPosition.y = (int) event.getY();
                Log.e(TAG, "onTouch_DOWN: X1 = " + mNewPosition.x + ", Y1 = " + mNewPosition.y);
                mRootPoint.x += mNewPosition.x;
                mRootPoint.y += mNewPosition.y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    float x2 = event.getX(0);
                    float y2 = event.getY(0);
                    float x3 = event.getX(1);
                    float y3 = event.getY(1);
                    Log.e(TAG, "onTouchEvent: X3 = " + x2 + ", Y3 = " + y2 + "; X3 = " + x3 + ", Y3 = " + y3);
                }
                break;
        }
        invalidate();
        return true;
    }
}
