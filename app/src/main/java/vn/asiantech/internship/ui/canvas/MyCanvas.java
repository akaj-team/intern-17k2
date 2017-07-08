package vn.asiantech.internship.ui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/7/2017.
 */
public class MyCanvas extends View {

    private int unit = 50;
    private Paint mPaint;
    private Point mPointO;
    private boolean mIsFirstTime;
    private Point mDownPoint;
    private Point mMidPoint;
    private Point mMidPointOnOxy;
    private double mOldDistance;

    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.i("tag11", Math.ceil(4.1) + "");
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (!mIsFirstTime) {
            mPointO.x = width / 2;
            mPointO.y = height / 2;
            mIsFirstTime = true;
        }

        //Ox
        if (mPointO.y >= 0 && mPointO.y <= height) {
            mPaint.setColor(Color.BLACK);
            canvas.drawLine(0, mPointO.y, width, mPointO.y, mPaint);
            canvas.drawLine(width - 10, mPointO.y - 10, width, mPointO.y, mPaint);
            canvas.drawLine(height - 10, mPointO.y + 10, width, mPointO.y, mPaint);
            mPaint.setStrokeWidth(3);
            mPaint.setColor(Color.RED);
            for (double i = Math.ceil((0 - mPointO.x) / unit); i <= Math.ceil((width - mPointO.x) / unit); i++) {
                canvas.drawPoint((float) (mPointO.x + i * unit), mPointO.y, mPaint);
            }
            mPaint.setStrokeWidth(1);
        }

        //Oy
        if (mPointO.x >= 0 && mPointO.x <= width) {
            mPaint.setColor(Color.BLACK);
            canvas.drawLine(mPointO.x, 0, mPointO.x, height, mPaint);
            canvas.drawLine(mPointO.x, 0, mPointO.x - 10, 10, mPaint);
            canvas.drawLine(mPointO.x, 0, mPointO.x + 10, 10, mPaint);
            mPaint.setStrokeWidth(3);
            mPaint.setColor(Color.RED);
            for (double i = Math.ceil((0 - mPointO.y) / unit); i <= Math.ceil((height - mPointO.y) / unit); i++) {
                canvas.drawPoint(mPointO.x, (float) (mPointO.y + i * unit), mPaint);
            }
            mPaint.setStrokeWidth(1);
        }
        Point oldPoint = fx(3, -4, 1, 0);
        Point newPoint;
        for (int i = 1; i < width; i++) {
            newPoint = fx(3, -4, 1, i);
            canvas.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y, mPaint);
            oldPoint = newPoint;
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1);
        mPointO = new Point();
        mDownPoint = new Point();
        mMidPoint = new Point();
        mMidPointOnOxy = new Point();
    }

    //f(x) = ax^2 + bx + c
    private Point fx(float a, float b, float c, int x) {
        Point point = new Point();
        float xOnOxy = (x - mPointO.x * 1f) / unit;
        double yOnOxy = a * Math.pow(xOnOxy, 2) + b * xOnOxy + c;
        double y = (mPointO.y - yOnOxy * unit);
        point.x = x;
        point.y = (int) y;
        return point;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.x = (int) event.getX();
                mDownPoint.y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 2) {
                    if (mOldDistance == 0) {
                        mOldDistance = getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    } else {
                        mMidPoint = getMidPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                        mMidPointOnOxy.x = (mMidPoint.x - mPointO.x) / unit;
                        mMidPointOnOxy.y = (mPointO.y - mMidPoint.y) / unit;
                        double newDistance = getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                        double proportion = newDistance / mOldDistance;
                        if (proportion * unit > 20 && proportion * unit < 200) {
                            zoomIn(newDistance / mOldDistance);
                        }
                    }
                } else if (event.getPointerCount() == 1) {
                    mPointO.x = mPointO.x + ((int) event.getX() - mDownPoint.x);
                    mPointO.y = mPointO.y + ((int) event.getY() - mDownPoint.y);
                    mDownPoint.x = (int) event.getX();
                    mDownPoint.y = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                mOldDistance = 0;
                break;
        }
        invalidate();
        return true;
    }

    public void zoomIn(double x) {
        unit = (int) (unit * x);
        mPointO.x = mMidPoint.x - mMidPointOnOxy.x * unit;
        mPointO.y = mMidPoint.y + mMidPointOnOxy.y * unit;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Log.i("tag11", e.getMessage());
        }
        invalidate();
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private Point getMidPoint(double x1, double y1, double x2, double y2) {
        return new Point((int) (x1 + x2) / 2, (int) (y1 + y2) / 2);
    }
}
