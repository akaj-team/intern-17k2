package vn.asiantech.internship.ui.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 *
 * Created by quanghai on 06/07/2017.
 */
public class CustomView extends View {
    private static final int MARGIN = 30;

    private int mUnit = 100;
    private Paint mPaint;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.0f;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX = getWidth() / 2;
    private float mPosY = getHeight() / 2;
    private float mDistance = 0;
    private float mScalePointX;
    private float mScalePointY;

    public CustomView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setTextSize(50);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(5);
        }
        Log.d("xxx", "onDraw: " + mScaleFactor);
        canvas.scale(mScaleFactor, mScaleFactor, mScalePointX, mScalePointY);
        drawPivotX(canvas);
        drawPivotY(canvas);
        drawArrow(canvas);
        drawParabol(canvas);
    }

    //y = 3x^2 - 4x + 1
    private void drawParabol(Canvas canvas) {
        float x = getWidth() / 2 + 10 * mUnit;
        float y = getHeight() / 2 - (3 * x * x - 4 * x + 1) * mUnit;
        for (double i = -10; i < 10; i += 0.05f) {
            float x2 = (float) (getWidth() / 2 + i * mUnit) + mPosX;
            float y2 = (float) (getHeight() / 2 - (3 * i * i - 4 * i + 1) * mUnit) + mPosY;
            canvas.drawLine(x, y, x2, y2, mPaint);
            x = x2;
            y = y2;
        }
    }

    private void drawArrow(Canvas canvas) {
        //draw 0
        canvas.drawText("0", getWidth() / 2 - 50 + mPosX, getHeight() / 2 + 50 + mPosY, mPaint);

        Path path = new Path();
        path.moveTo(getWidth() - MARGIN + mDistance, getHeight() / 2 - 10 + mPosY);
        path.lineTo(getWidth() + mDistance, getHeight() / 2 + mPosY);
        path.lineTo(getWidth() - MARGIN + mDistance, getHeight() / 2 + 10 + mPosY);

        path.moveTo(getWidth() / 2 + 10 + mPosX, MARGIN + mDistance);
        path.lineTo(getWidth() / 2 + mPosX, 0 + mDistance);
        path.lineTo(getWidth() / 2 - 10 + mPosX, MARGIN + mDistance);
        canvas.drawPath(path, mPaint);
    }

    private void drawPivotX(Canvas canvas) {
        // canvas.drawLine(0 + mPosX, getHeight() / 2 + mPosY, getWidth() mPosX, getHeight() / 2 + mPosY, mPaint);
        canvas.drawLine(0 + mDistance, getHeight() / 2 + mPosY, getWidth() + mDistance, getHeight() / 2 + mPosY, mPaint);
        for (float i = getWidth() / 2; i < getWidth() - mPosX; i += mUnit) {
            canvas.drawLine(i + mPosX, getHeight() / 2 + 10 + mPosY, i + mPosX, getHeight() / 2 - 10 + mPosY, mPaint);
        }
        for (float i = getWidth() / 2; i > 0 - mPosX; i -= mUnit) {
            canvas.drawLine(i + mPosX, getHeight() / 2 + 10 + mPosY, i + mPosX, getHeight() / 2 - 10 + mPosY, mPaint);
        }
    }

    private void drawPivotY(Canvas canvas) {
        canvas.drawLine(getWidth() / 2 + mPosX, 0 + mDistance, getWidth() / 2 + mPosX, getHeight() + mDistance, mPaint);
        for (float i = getHeight() / 2; i < getHeight() - mPosY; i += mUnit) {
            canvas.drawLine(getWidth() / 2 - 10 + mPosX, i + mPosY, getWidth() / 2 + 10 + mPosX, i + mPosY, mPaint);
        }
        for (float i = getHeight() / 2; i > 0 - mPosY; i -= mUnit) {
            canvas.drawLine(getWidth() / 2 - 10 + mPosX, i + mPosY, getWidth() / 2 + 10 + mPosX, i + mPosY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() > 1) {
                    mScaleDetector.onTouchEvent(event);
                } else {
                    Log.d("xxx", "onTouchEvent: move");
                    float x = event.getX();
                    float y = event.getY();

                    float dx = x - mLastTouchX;
                    float dy = y - mLastTouchY;
                    mDistance = dx;

                    mPosX += dx;
                    mPosY += dy;
                    invalidate();

                    mLastTouchX = x;
                    mLastTouchY = y;
                }
                break;
        }
        return true;
    }

    /**
     * Created by quanghai on 06/07/2017.
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScalePointX = detector.getFocusX();
            mScalePointY = detector.getFocusY();
            mScaleFactor = Math.max(1f, Math.min(mScaleFactor, 10.0f));
            invalidate();
            return true;
        }
    }
}
