package vn.asiantech.internship.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 06/07/2017.
 */
public class CustomView extends View {
    private static final int MARGIN = 50;
    private static final int STROKE_WITH = 2;
    private static final int EXTRA_LENGTH = 10;
    private static final int TEXT_SIZE = 30;
    private ArrayList<Point> mPoints = new ArrayList<>();
    private Paint mPaint;
    private Paint mPathPaint;
    private Path mPath;
    private Point mTransPoint;
    private Point mStartPoint;
    private Point mCenterPoint;
    private float mCenterX;
    private float mCenterY;
    private float mDistance;
    private float mUnit = 50;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPathPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCenterX == 0) {
            mCenterX = getWidth() / 2;
            mCenterY = getHeight() / 2;
        }
        drawAxis(canvas);
        drawNarrow(canvas);
        calculate(3, -4, 1);
        drawGraph(canvas);
        for (int i = (int) ((MARGIN - mCenterY) / mUnit); i < (getHeight() - MARGIN - mCenterY) / mUnit; i++) {
            drawVerticalNumber(canvas, i);
        }
        for (int i = (int) ((MARGIN - mCenterX) / mUnit); i < (int) ((getWidth() - MARGIN) - mCenterX) / mUnit; i++) {
            drawHorizontalNumber(canvas, i);
        }
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(STROKE_WITH);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(TEXT_SIZE);
    }

    private void initPathPaint() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.BLACK);
        mPathPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    private void drawAxis(Canvas canvas) {
        canvas.drawLine(0, mCenterY, getWidth(), mCenterY, mPaint);
        canvas.drawLine(mCenterX, 0, mCenterX, getHeight(), mPaint);
    }

    private void drawNarrow(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(mCenterX - EXTRA_LENGTH, EXTRA_LENGTH);
        mPath.lineTo(mCenterX + EXTRA_LENGTH, EXTRA_LENGTH);
        mPath.lineTo(mCenterX, 0);

        mPath.moveTo(getWidth() - EXTRA_LENGTH, mCenterY - EXTRA_LENGTH);
        mPath.lineTo(getWidth() - EXTRA_LENGTH, mCenterY + EXTRA_LENGTH);
        mPath.lineTo(getWidth(), mCenterY);

        canvas.drawPath(mPath, mPathPaint);
        canvas.drawText("O", mCenterX - EXTRA_LENGTH * 4, mCenterY + EXTRA_LENGTH * 4, mPaint);
    }

    private void calculate(int a, int b, int c) {
        for (double i = -getWidth() / mUnit; i < getWidth() / mUnit; i = i + 0.1f) {
            mPoints.add(new Point((float) (mCenterX + i * mUnit), (float) (mCenterY - (a * i * i + b * i + c) * mUnit)));
        }
    }

    private void drawGraph(Canvas canvas) {
        for (int i = 0; i < mPoints.size() - 1; i++) {
            canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY(), mPaint);
        }
    }

    private void drawHorizontalNumber(Canvas canvas, int i) {
        canvas.drawLine(mCenterX + mUnit * i, mCenterY + 5, mCenterX + mUnit * i, mCenterY - 5, mPaint);
    }

    private void drawVerticalNumber(Canvas canvas, int i) {
        canvas.drawLine(mCenterX - 5, mCenterY + mUnit * i, mCenterX + 5, mCenterY + mUnit * i, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    mStartPoint = new Point(event.getX(), event.getY());
                } else {
                    mStartPoint = new Point(getWidth() / 2, getHeight() / 2);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    if (mDistance == 0) {
                        mTransPoint = new Point(event.getX(), event.getY());
                        Point point = new Point(mTransPoint.getX() - mStartPoint.getX(), mTransPoint.getY() - mStartPoint.getY());
                        mCenterX += point.getX();
                        mCenterY += point.getY();
                    } else {
                        if (mCenterPoint != null) {
                            mTransPoint = new Point(mCenterPoint.getX(), mCenterPoint.getY());
                        }
                    }
                } else if (event.getPointerCount() == 2) {
                    if (mDistance == 0) {
                        float x = event.getX(1) - event.getX(0);
                        float y = event.getY(1) - event.getY(0);
                        mDistance = (float) Math.sqrt(x * x + y * y);
                    } else {
                        float x = event.getX(1) - event.getX(0);
                        float y = event.getY(1) - event.getY(0);
                        mCenterPoint = new Point((event.getX(1) - event.getX(0)) / 2, (event.getY(1) - event.getY(0)) / 2);
                        if (((float) Math.sqrt(x * x + y * y)) != mDistance) {
                            float scale = ((float) Math.sqrt(x * x + y * y)) / (mDistance);
                            if (scale * mUnit > 10 && scale * mUnit < 400) {
                                mUnit *= scale;
                            }
                            mDistance = (float) Math.sqrt(x * x + y * y);
                        }
                    }
                }
                mStartPoint = mTransPoint;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mDistance = 0;
                mStartPoint = new Point(event.getX(), event.getY());
                break;
        }
        mPoints.clear();
        return true;
    }
}
