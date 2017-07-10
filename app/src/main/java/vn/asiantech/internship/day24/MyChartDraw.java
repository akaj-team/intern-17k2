package vn.asiantech.internship.day24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 07/07/2017.
 */
public class MyChartDraw extends View {
    private static final int MARGIN_LEFT = 50;
    private static final int MARGIN_RIGHT = 10;
    private static final int MARGIN_TOP = 50;
    private static final int SCALE = 30;

    private static final float MIN_ZOOM = 1f;
    private static final float MAX_ZOOM = 4f;

    private float scaleFactor = 1f;
    private ScaleGestureDetector detector;

    // These constants specify the mMode that we're in
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mMode = DRAG;

    // These two variables keep track of the X and Y coordinate of the finger when it first
    // touches the screen
    private float mStartX = 0f;
    private float mStartY = 0f;

    // These two variables keep track of the amount we need to translate the canvas along the X
    // and the Y coordinate
    private float mTranslateX = 0f;
    private float mTranslateY = 0f;

    // These two variables keep track of the amount we translated the X and Y coordinates, the last time we
    // panned.
    private float mPreviousTranslateX = 0f;
    private float mPreviousTranslateY = 0f;
    private boolean dragged;

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
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public MyChartDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mMode = DRAG;
                mStartX = event.getX() - mPreviousTranslateX;
                mStartY = event.getY() - mPreviousTranslateY;
                break;

            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;

                double distance = Math.sqrt(Math.pow(event.getX() - (mStartX + mPreviousTranslateX), 2) +
                        Math.pow(event.getY() - (mStartY + mPreviousTranslateY), 2)
                );

                if (distance > 0) {
                    dragged = true;
                }

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                dragged = false;
                break;

            case MotionEvent.ACTION_UP:
                mMode = NONE;
                dragged = false;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                dragged = false;
                mMode = DRAG;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;
        }

        detector.onTouchEvent(event);

        if ((mMode == DRAG && scaleFactor != 1f && dragged) || mMode == ZOOM) {
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // We're going to scale the X and Y coordinates by the same amount
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);

        // If mTranslateX times -1 is lesser than zero, let's set it to zero. This takes care of the left bound
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        } else if ((mTranslateX * -1) > (scaleFactor - 1) * getWidth()) {
            mTranslateX = (1 - scaleFactor) * getWidth();
        }

        if (mTranslateY * -1 < 0) {
            mTranslateY = 0;
        } else if ((mTranslateY * -1) > (scaleFactor - 1) * getHeight()) {
            mTranslateY = (1 - scaleFactor) * getHeight();
        }

        // Create Coordinate axis
        canvas.drawLine((MARGIN_LEFT - 30 + mTranslateX), getHeight() / 2 + mTranslateY, (getWidth() - MARGIN_RIGHT + mTranslateX), getHeight() / 2 + mTranslateY, mPaint1);
        if (mPath1 == null) {
            mPath1 = new Path();
        }
        mPath1.reset();
        mPath1.moveTo(getWidth() - MARGIN_RIGHT - 20 + mTranslateX, getHeight() / 2 + 20 + mTranslateY);
        mPath1.lineTo(getWidth() - MARGIN_RIGHT - 20 + mTranslateX, getHeight() / 2 - 20 + mTranslateY);
        mPath1.lineTo(getWidth() - MARGIN_RIGHT + 10 + mTranslateX, getHeight() / 2 + mTranslateY);
        canvas.drawPath(mPath1, mPaint1);

        canvas.drawLine(getWidth() / 2 + mTranslateX, MARGIN_TOP + mTranslateY, getWidth() / 2 + mTranslateX, getHeight() * 3 / 4 + MARGIN_TOP + mTranslateY, mPaint1);

        if (mPath2 == null) {
            mPath2 = new Path();
        }
        mPath2.reset();
        mPath2.moveTo(getWidth() / 2 - 20 + mTranslateX, MARGIN_TOP + 20 + mTranslateY);
        mPath2.lineTo(getWidth() / 2 + 20 + mTranslateX, MARGIN_TOP + 20 + mTranslateY);
        mPath2.lineTo(getWidth() / 2 + mTranslateX, MARGIN_TOP - 10 + mTranslateY);
        canvas.drawPath(mPath2, mPaint1);

        canvas.drawText(getContext().getString(R.string.goc_O), getWidth() / 2 - 40 + mTranslateX, getHeight() / 2 + 40 + mTranslateY, mPaint1);
        canvas.drawText(getContext().getString(R.string.truc_X), getWidth() - MARGIN_RIGHT - 30 + mTranslateX, getHeight() / 2 + 50 + mTranslateY, mPaint1);
        canvas.drawText(getContext().getString(R.string.truc_Y), getWidth() / 2 - 50 + mTranslateX, MARGIN_TOP + 50 + mTranslateY, mPaint1);

        ptDoThi(3, -4, 1, canvas);
        canvas.translate(mTranslateX / scaleFactor, mTranslateY / scaleFactor);
        canvas.restore();
    }

    private void initPaint1() {
        mPaint1 = new Paint();
        mPaint1.setStrokeWidth(3);
        mPaint1.setTextSize(30);
        mPaint1.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    private void initPaint2() {
        mPaint2 = new Paint();
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeWidth(2);
        mPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setColor(Color.RED);
    }

    private void ptDoThi(float a, float b, float c, Canvas canvas) {
        float width = getWidth() / SCALE;
        float xT = -width / 2;
        float yT = a * xT * xT + b * xT + c;

        for (float i = -width / 2; i < width / 2; i += 0.05f) {
            float y = a * i * i + b * i + c;
            if ((getHeight() / 2 - yT * SCALE) < getHeight() && (getHeight() / 2 - yT * SCALE) > 0 && (getHeight() / 2 - y * SCALE) < getHeight() && (getHeight() / 2 - y * SCALE) > 0) {
                canvas.drawLine(getWidth() / 2 + xT * SCALE + mTranslateX, getHeight() / 2 - yT * SCALE + mTranslateY, getWidth() / 2 + i * SCALE + mTranslateX, getHeight() / 2 - y * SCALE + mTranslateY, mPaint2);
            }
            xT = i;
            yT = y;
        }
    }

    /**
     * Create ScaleListener
     */
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            return true;
        }
    }
}
