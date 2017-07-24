package vn.asiantech.internship.day24;

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

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 07/07/2017.
 */
public class MyChartDraw extends View implements View.OnTouchListener {
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int SCALE = 40;
    private float mZoomFactor = 1f;
    private int mMode = NONE;

    private float mTranslateX = 0f;
    private float mTranslateY = 0f;
    private float mPreviousTranslateX = 0f;
    private float mPreviousTranslateY = 0f;

    private boolean mFirstDown;
    private Point mStartPoint = new Point();
    private Point mLastPoint = new Point();
    private double mNewDistance;
    private double mOldDistance;

    private Paint mPaint1;
    private Paint mPaint2;
    private Path mPath1;
    private Path mPath2;
    private Paint mPaint3;

    public MyChartDraw(Context context) {
        this(context, null);
    }

    public MyChartDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint1();
        initPaint2();
        initPaint3();
        setOnTouchListener(this);
    }

    public MyChartDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Create Coordinate axis
        canvas.drawLine(0, (getHeight() / 2) + mTranslateY, getWidth(), (getHeight() / 2) + mTranslateY, mPaint1);
        canvas.drawLine((getWidth() / 2) + mTranslateX, 0, (getWidth() / 2) + mTranslateX, getHeight(), mPaint1);

        canvas.drawText(getContext().getString(R.string.goc_O), (getWidth() / 2) - 40 + mTranslateX, (getHeight() / 2) + 40 + mTranslateY, mPaint1);
        canvas.drawText(getContext().getString(R.string.truc_X), getWidth() - 30, (getHeight() / 2) + 50 + mTranslateY, mPaint1);
        canvas.drawText(getContext().getString(R.string.truc_Y), (getWidth() / 2) - 50 + mTranslateX, 50, mPaint1);

        if (mPath1 == null) {
            mPath1 = new Path();
        }
        mPath1.reset();
        mPath1.moveTo(getWidth() - 20, (getHeight() / 2 + 20) + mTranslateY);
        mPath1.lineTo(getWidth() - 20, (getHeight() / 2 - 20) + mTranslateY);
        mPath1.lineTo(getWidth(), (getHeight() / 2) + mTranslateY);
        canvas.drawPath(mPath1, mPaint1);
        if (mPath2 == null) {
            mPath2 = new Path();
        }
        mPath2.reset();
        mPath2.moveTo(getWidth() / 2 - 20 + mTranslateX, 20);
        mPath2.lineTo(getWidth() / 2 + 20 + mTranslateX, 20);
        mPath2.lineTo(getWidth() / 2 + mTranslateX, 0);
        canvas.drawPath(mPath2, mPaint1);

        ptDoThi(3, -4, 1, canvas);
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

    private void initPaint3() {
        mPaint3 = new Paint();
        mPaint3.setStrokeWidth(7);
        mPaint3.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setColor(Color.RED);
    }


    private void ptDoThi(float a, float b, float c, Canvas canvas) {
        double width = getWidth() / SCALE;
        double xT = -width / 2;
        double yT = a * xT * xT + b * xT + c;

        for (double i = -width / 2; i < width / 2; i += 0.05f) {
            double y = a * i * i + b * i + c;
            canvas.drawLine((float) (getWidth() / 2 + xT * SCALE * mZoomFactor + mTranslateX), (float) (getHeight() / 2 - yT * SCALE * mZoomFactor + mTranslateY), (float) (getWidth() / 2 + i * SCALE * mZoomFactor + mTranslateX), (float) (getHeight() / 2 - y * SCALE * mZoomFactor + mTranslateY), mPaint2);
            xT = i;
            yT = y;
        }

        for (double i = -width + 1; i < width - 1; i++) {
            canvas.drawPoint((float) (getWidth() / 2 + SCALE * i * mZoomFactor + mTranslateX), getHeight() / 2 + mTranslateY, mPaint3);
        }

        for (double i = -width + 1; i < width - 1; i++) {
            canvas.drawPoint((getWidth() / 2 + mTranslateX), (float) (getHeight() / 2 + SCALE * i * mZoomFactor + mTranslateY), mPaint3);
        }

    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mStartPoint.x = (int) (event.getX());
                mStartPoint.y = (int) (event.getY());
                mMode = DRAG;
                performClick();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mMode = ZOOM;
                if (!mFirstDown) {
                    mLastPoint.x = (int) ((event.getX(0) + event.getX(1)) / 2);
                    mLastPoint.y = (int) ((event.getY(0) + event.getY(1)) / 2);
                    mOldDistance = getDistance(event.getX(1), event.getY(1), event.getX(0), event.getY(0));
                    mFirstDown = true;
                }
                performClick();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == ZOOM) {
                    mNewDistance = getDistance(event.getX(1), event.getY(1), event.getX(0), event.getY(0));
                    mZoomFactor *= (float) (mNewDistance / mOldDistance);
                } else if (mMode == DRAG) {
                    mTranslateX = event.getX() - mStartPoint.x + mPreviousTranslateX;
                    mTranslateY = event.getY() - mStartPoint.y + mPreviousTranslateY;
                }
                mOldDistance = mNewDistance;
                break;
            case MotionEvent.ACTION_UP:
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                mFirstDown = false;
                performClick();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mMode = NONE;
                performClick();
                break;
        }
        if (mZoomFactor < 0.5) {
            mZoomFactor = (float) 0.5;
        }
        if (mZoomFactor > 7) {
            mZoomFactor = 7;
        }
        invalidate();
        return true;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
