package vn.asiantech.internship.exday24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by datbu on 10-07-2017.
 */
public class CustomView extends View {
    private static final int MARGIN = 30;
    private Path mPath;
    private int unit = 100;
    private Paint mPaint;
    private Point mPointO;
    private boolean mIsDraw;
    private Point mDownPoint;
    private Point mMidPoint;
    private Point mMidPointOnMove;
    private double mOldDistance;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (!mIsDraw) {
            mPointO.x = width / 2;
            mPointO.y = height / 2;
            mIsDraw = true;
        }

        // Draw Ox
        if (mPointO.y >= 0 && mPointO.y <= height) {
            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(30);
            canvas.drawText("O", mPointO.x - 30, mPointO.y + 30, mPaint);
            mPaint.setStrokeWidth(4);
            canvas.drawLine(0, mPointO.y, width, mPointO.y, mPaint);
            canvas.drawLine(width - 10, mPointO.y - 10, width, mPointO.y, mPaint);
            canvas.drawLine(width - 10, mPointO.y + 10, width, mPointO.y, mPaint);
            for (double i = Math.ceil((0.0 - mPointO.x) / unit); i <= Math.ceil((width * 1.0 - mPointO.x) / unit); i++) {
                mPaint.setColor(Color.RED);
                mPaint.setStrokeWidth(3);
                canvas.drawCircle((float) (mPointO.x + i * unit), mPointO.y, 3, mPaint);
                if ((int) i != 0 && (int) i < width - 30) {
                    initColor();
                    canvas.drawText("x", width - 30, mPointO.y - 30, mPaint);
                    canvas.drawText(String.valueOf((int) i), (float) (mPointO.x + i * unit), mPointO.y + 30, mPaint);
                    mPaint.setStrokeWidth(1);
                }
            }
            mPaint.setStrokeWidth(2);
        }

        // Draw Oy
        if (mPointO.x >= 0 && mPointO.x <= width) {
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(4);
            canvas.drawLine(mPointO.x, 0, mPointO.x, height, mPaint);
            canvas.drawLine(mPointO.x, 0, mPointO.x - 10, 10, mPaint);
            canvas.drawLine(mPointO.x, 0, mPointO.x + 10, 10, mPaint);
            for (double i = Math.ceil((0.0 - mPointO.y) / unit); i <= Math.ceil((height * 1.0 - mPointO.y) / unit); i++) {
                mPaint.setColor(Color.RED);
                mPaint.setStrokeWidth(3);
                canvas.drawCircle(mPointO.x, (float) (mPointO.y + i * unit), 3, mPaint);
                if ((int) i != 0) {
                    initColor();
                    canvas.drawText("y", mPointO.x + 30, height / 50, mPaint);
                    canvas.drawText(String.valueOf((int) -i), mPointO.x - 30, (float) (mPointO.y + i * unit), mPaint);
                    mPaint.setStrokeWidth(1);
                }
            }
            mPaint.setStrokeWidth(2);
        }
        mPaint.setColor(Color.RED);
        Point oldPoint = equation(3, -4, 1, 0);
        Point newPoint;
        for (int i = 1; i < width; i++) {
            newPoint = equation(3, -4, 1, i);
            canvas.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y, mPaint);
            oldPoint = newPoint;
        }
    }

    private void initColor() {
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(20);
        mPaint.setColor(Color.BLACK);
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1);
        mPointO = new Point();
        mDownPoint = new Point();
        mMidPoint = new Point();
        mMidPointOnMove = new Point();
        mPath = new Path();
    }

    //f(x) = ax^2 + bx + c
    private Point equation(float a, float b, float c, int x) {
        Point point = new Point();
        float newX = (x - mPointO.x * 1f) / unit;
        double newY = a * Math.pow(newX, 2) + b * newX + c;
        double y = (mPointO.y - newY * unit);
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
                        mMidPoint = getO(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                        mMidPointOnMove.x = (mMidPoint.x - mPointO.x) / unit;
                        mMidPointOnMove.y = (mPointO.y - mMidPoint.y) / unit;
                    }
                } else if (event.getPointerCount() == 1) {
                    mPointO.x = mPointO.x + ((int) event.getX() - mDownPoint.x);
                    mPointO.y = mPointO.y + ((int) event.getY() - mDownPoint.y);
                    mDownPoint.x = (int) event.getX();
                    mDownPoint.y = (int) event.getY();
                }
                break;
        }
        invalidate();
        return true;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private Point getO(double x1, double y1, double x2, double y2) {
        return new Point((int) (x1 + x2) / 2, (int) (y1 + y2) / 2);
    }
}
