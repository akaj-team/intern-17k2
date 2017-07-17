package vn.asiantech.internship.bai24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.ui.CustomView;

/**
 * Created by at-dinhvo on 07/07/2017.
 */
public class GraphView extends CustomView {
    private static final String TAG = "at-dinhvo";
    private final float mStep = 0.3f;
    private Paint mPaint;
    private int mDistance = 30;
    private int mRatio = 30;
    private Point mNewPosition;
    private Point mRootPoint = new Point();

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
        mRootPoint.x = getWidth() / 2;
        mRootPoint.y = getHeight() / 2;
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
        Point mOldPoint = getGraphPoint(getWidth() / 3 - mStep);
        Point mNewPoint;
        for (float i = getWidth() / 3; i < 2 * getWidth() / 3; i += mStep) {
            mNewPoint = getGraphPoint(i);
            canvas.drawLine(mOldPoint.x, mOldPoint.y, mNewPoint.x, mNewPoint.y, mPaint);
            mOldPoint = mNewPoint;
        }
    }

    private Point getGraphPoint(float x) {
        Point point = new Point();
        float X = (x - getWidth() / 2) / mRatio;
        float Y = (3 * X * X - 4 * X + 1);
        float y = (getHeight() / 2 - Y * mRatio);
        point.x = (int) x;
        point.y = (int) y;
//        Log.e(TAG, "getGraphPoint: X = " + x + ", Y = " + y);
        return point;
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
/*
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
                mNewPosition = event.getY();
                Log.e(TAG, "onTouch_DOWN: X1 = " + x1 + ", Y1 = " + y1);
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
    }*/
}
