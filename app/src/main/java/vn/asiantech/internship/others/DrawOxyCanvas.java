package vn.asiantech.internship.others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Canvas draw f(x) = 3x^2 - 4x + 1
 * Created by huypham on 14-Jul-17.
 */
public class DrawOxyCanvas extends View {
    private int unit = 50;
    private Paint mPaint;
    private Paint mPainText;
    private Point mPointO;
    private boolean mIsFirst;
    private Point mMidPoint;
    private Point mMidPointOnOxy;
    private Point mDownPoint;
    private double mDistance;

    public DrawOxyCanvas(Context context) {
        this(context, null);
    }

    public DrawOxyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (!mIsFirst) {
            mPointO.x = width / 2;
            mPointO.y = height / 2;
            mIsFirst = true;
        }

        // Draw Ox
        if (mPointO.y >= 0 && mPointO.y <= height) {
            mPaint.setColor(Color.BLACK);
            canvas.drawLine(0, mPointO.y, width, mPointO.y, mPaint);

            mPaint.setStrokeWidth(5);
            canvas.drawLine(width - 10, mPointO.y - 10, width, mPointO.y, mPaint);
            canvas.drawLine(width - 10, mPointO.y + 10, width, mPointO.y, mPaint);

            mPaint.setColor(Color.RED);
            for (double i = Math.ceil((0.0 - mPointO.x) / unit); i <= Math.ceil((width * 1.0 - mPointO.x) / unit); i++) {
                canvas.drawPoint((float) (mPointO.x + i * unit), mPointO.y, mPaint);
            }

            mPaint.setStrokeWidth(2);
            canvas.drawText("x", width - 30, mPointO.y + 20, mPainText);
        }

        // Draw Oy
        if (mPointO.x >= 0 && mPointO.x <= width) {
            mPaint.setColor(Color.BLACK);
            canvas.drawLine(mPointO.x, 0, mPointO.x, height, mPaint);

            mPaint.setStrokeWidth(5);
            canvas.drawLine(mPointO.x - 10, 10, mPointO.x, 0, mPaint);
            canvas.drawLine(mPointO.x + 10, 10, mPointO.x, 0, mPaint);

            mPaint.setColor(Color.RED);
            for (double i = Math.ceil((0.0 - mPointO.y) / unit); i <= Math.ceil((height * 1.0 - mPointO.y) / unit); i++) {
                canvas.drawPoint(mPointO.x, (float) (mPointO.y + i * unit), mPaint);
            }

            mPaint.setStrokeWidth(2);
            canvas.drawText("y", mPointO.x + 20, 30, mPainText);
        }

        // Draw text O
        canvas.drawText("O", mPointO.x - 30, mPointO.y + 30, mPainText);

        // Draw f(x)
        mPaint.setColor(Color.BLUE);
        Point startPoint = fx(3, -4, 1, 0);
        Point drawPoint;
        for (int i = 1; i < width; i++) {
            drawPoint = fx(3, -4, 1, i);
            canvas.drawLine(startPoint.x, startPoint.y, drawPoint.x, drawPoint.y, mPaint);
            startPoint = drawPoint;
        }
    }

    // f(x) = ax^2 + bx + c
    private Point fx(float a, float b, float c, int x) {
        Point point = new Point();
        float pointXOnOxy = (x - mPointO.x * 1f) / unit;
        double pointYOnOxy = a * Math.pow(pointXOnOxy, 2) + b * pointXOnOxy + c;
        double y = mPointO.y - pointYOnOxy * unit;
        point.x = x;
        point.y = (int) y;
        return point;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(2);
        mPointO = new Point();

        mPainText = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPainText.setColor(Color.RED);
        mPainText.setTextSize(30);
        mPainText.setStrokeWidth(1);

        mMidPoint = new Point();
        mMidPointOnOxy = new Point();
        mDownPoint = new Point();
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
                    if (mDistance == 0) {
                        mDistance = getDistance(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                    } else {
                        mMidPoint = getMidPoint(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                        mMidPointOnOxy.x = (mMidPoint.x - mPointO.x) / unit;
                        mMidPointOnOxy.y = (mPointO.y - mMidPoint.y) / unit;
                        double newDistance = getDistance(event.getX(0), event.getX(1), event.getY(0), event.getY(1));
                        double ratio = newDistance / mDistance;
                        if (ratio * unit > 20 && ratio * unit < 200) {
                            zoom(ratio);
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
                mDistance = 0;
                break;
        }
        invalidate();
        return true;
    }

    private void zoom(double x) {
        unit = (int) (unit * x);
        mPointO.x = mMidPoint.x - mMidPointOnOxy.x * unit;
        mPointO.y = mMidPoint.y + mMidPointOnOxy.y * unit;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Log.e("Zoom", e.getMessage());
        }
        invalidate();
    }

    private double getDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private Point getMidPoint(double x1, double x2, double y1, double y2) {
        return new Point((int) (x1 + x2) / 2, (int) (y1 + y2) / 2);
    }
}
