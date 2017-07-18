package vn.asiantech.internship.ui.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ducle on 11/07/2017.
 * DrawParabolaView is view draw parabola
 */
public class DrawParabolaView extends View {
    private static final int INVALID_POINTER_ID = -1;
    private int mFirstPointerId = INVALID_POINTER_ID;
    private static final String TAG = DrawParabolaView.class.getSimpleName();
    private Paint mPaint;
    private Path mPath;
    private float mOriginX = 0;
    private float mOriginY = 0;
    private float mFirstHoldX;
    private float mFirstHoldY;
    private float mSecondHoldX;
    private float mSecondHoldY;
    private float mPivotPointX = 0;
    private float mPivotPointY = 0;
    private float mDistance;
    private boolean mKeep = true;
    private float k = 100;

    public DrawParabolaView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
    }

    public DrawParabolaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.BLACK);
        canvas.translate(mOriginX, mOriginY);

        //draw 2 lines
        canvas.drawLine(getWidth() / 2 - 100 * k, getHeight() / 2, getWidth() / 2 + 100 * k, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, getHeight() / 2 - 100 * k, getWidth() / 2, getHeight() / 2 + 100 * k, mPaint);

        //draw 2 arrows
        mPath.moveTo(getWidth() - 50 - mOriginX, getHeight() / 2 - 10);
        mPath.lineTo(getWidth() - 50 - mOriginX, getHeight() / 2 + 10);
        mPath.lineTo(getWidth() - mOriginX, getHeight() / 2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();

        mPath.moveTo(getWidth() / 2 - 10, 50 - mOriginY);
        mPath.lineTo(getWidth() / 2 + 10, 50 - mOriginY);
        mPath.lineTo(getWidth() / 2, 0 - mOriginY);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
        mPaint.setStyle(Paint.Style.STROKE);

        //draw points
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        for (int j = 0; j < 100; j++) {
            canvas.drawPoint(getWidth() / 2, getHeight() / 2 + j * k, mPaint);
            canvas.drawPoint(getWidth() / 2, getHeight() / 2 - j * k, mPaint);
            canvas.drawPoint(getWidth() / 2 + j * k, getHeight() / 2, mPaint);
            canvas.drawPoint(getWidth() / 2 - j * k, getHeight() / 2, mPaint);
        }
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        float x = -5;
        float y = getY(-5);
        for (double i = -5; i < 5; i += 0.01f) {
            canvas.drawLine(getXCanvas(x, k), getYCanvas(y, k), getXCanvas((float) i, k), getYCanvas(getY((float) i), k), mPaint);
            x = (float) i;
            y = getY((float) i);
        }
    }

    private float getXCanvas(float x, float k) {
        return k * x + getWidth() / 2;
    }

    private float getYCanvas(float y, float k) {
        return -k * y + getHeight() / 2;
    }

    private float getY(float x) {
        return 3 * x * x - 4 * x + 1;
    }

    public void move(float oX, float oY) {
        mOriginX += oX;
        mOriginY += oY;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mFirstPointerId = event.getPointerId(0);
                mFirstHoldX = event.getX();
                mFirstHoldY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointerFirst = event.findPointerIndex(mFirstPointerId);
                if (event.getPointerCount() == 1) {
                    move(event.getX(pointerFirst) - mFirstHoldX, event.getY(pointerFirst) - mFirstHoldY);
                    mFirstHoldX = event.getX(pointerFirst);
                    mFirstHoldY = event.getY(pointerFirst);
                } else if (event.getPointerCount() == 2) {
                    if (mKeep) {
                        mSecondHoldX = event.getX(event.getPointerId(1));
                        mSecondHoldY = event.getY(event.getPointerId(1));
                        mDistance = getDistance(mFirstHoldX, mFirstHoldY, mSecondHoldX, mSecondHoldY);
                        mKeep = false;
                    } else {
                        final int pointerSecond = event.findPointerIndex(event.getPointerId(1));
                        float newDistance = getDistance(event.getX(pointerFirst), event.getY(pointerFirst), event.getX(pointerSecond
                        ), event.getY(pointerSecond));
                        if (newDistance > mDistance) {
                            k += 10;
                            invalidate();
                            Log.d(TAG, "zoom out: ");
                            mDistance = newDistance;
                        } else {
                            if (k > 10) {
                                k -= 10;
                                invalidate();
                                Log.d(TAG, "zoom in: ");
                                mDistance = newDistance;
                            }
                        }
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mFirstPointerId = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_CANCEL:
                mFirstPointerId = INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // Extract the index of the pointer that left the touch sensor
                final int pointerIndex0 = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex0);
                if (pointerId == mFirstPointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex0 == 0 ? 1 : 0;
                    mFirstHoldX = event.getX(newPointerIndex);
                    mFirstHoldY = event.getY(newPointerIndex);
                    mFirstPointerId = event.getPointerId(newPointerIndex);
                }
                break;
        }
        return true;
    }

    private float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) + Math.pow(Math.abs(y1 - y2), 2));
    }
}
