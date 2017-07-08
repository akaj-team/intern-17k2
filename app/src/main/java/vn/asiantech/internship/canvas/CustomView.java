package vn.asiantech.internship.canvas;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Author AsianTech Inc.
 * Created by at-hangtran on 06/07/2017.
 */
public class CustomView extends View {
    private static final int MARGIN = 50;
    private static final int STROKE_WITH = 2;
    private static final int EXTRA_LENGHT = 10;
    private static final int TEXT_SIZE = 30;
    private float scaleFactor = 1.0f;
    private List<Point> mPoints = new ArrayList<>();

    private ScaleGestureDetector mScaleGestureDetector;
    private Paint mPaint;
    private Paint mPathPaint;
    private Path mPath;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPathPaint();
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        drawAxis(canvas);
        drawNarrow(canvas);

        calculate(3, -4, 1);
        drawGraph(canvas);
        for (int i = -7; i < 7; i++) {
            if (i > -4 && i < 4) {
                drawVerticalNumber(canvas, i);
                drawHorizontalNumber(canvas, i);
            } else {
                drawVerticalNumber(canvas, i);
            }
        }

        canvas.restore();
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
        canvas.drawLine(MARGIN, getHeight() / 2, getWidth() - MARGIN, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, MARGIN, getWidth() / 2, getHeight() - MARGIN, mPaint);
    }

    private void drawNarrow(Canvas canvas) {
        mPath.moveTo(getWidth() / 2 - EXTRA_LENGHT, MARGIN);
        mPath.lineTo(getWidth() / 2 + EXTRA_LENGHT, MARGIN);
        mPath.lineTo(getWidth() / 2, MARGIN - EXTRA_LENGHT);

        mPath.moveTo(getWidth() - MARGIN, getHeight() / 2 - EXTRA_LENGHT);
        mPath.lineTo(getWidth() - MARGIN, getHeight() / 2 + EXTRA_LENGHT);
        mPath.lineTo(getWidth() - MARGIN + EXTRA_LENGHT, getHeight() / 2);

        canvas.drawPath(mPath, mPathPaint);
        canvas.drawText("O", getWidth() / 2 - EXTRA_LENGHT * 4, getHeight() / 2 + EXTRA_LENGHT * 4, mPaint);
    }

    private void calculate(int a, int b, int c) {
        for (float i = -3; i < 3; i = i + 0.05f) {
            mPoints.add(new Point(getWidth() / 2 + i * 50, getHeight() / 2 - (a * i * i + b * i + c) * 50));
        }
    }

    private void drawGraph(Canvas canvas) {
        for (int i = 0; i < mPoints.size() - 1; i++) {
            canvas.drawLine(mPoints.get(i).getX(), mPoints.get(i).getY(), mPoints.get(i + 1).getX(), mPoints.get(i + 1).getY(), mPaint);
        }
    }

    private void drawHorizontalNumber(Canvas canvas, int i) {
        canvas.drawLine(getWidth() / 2 + MARGIN * i, getHeight() / 2 + 5, getWidth() / 2 + MARGIN * i, getHeight() / 2 - 5, mPaint);
    }

    private void drawVerticalNumber(Canvas canvas, int i) {
        canvas.drawLine(getWidth() / 2 - 5, getHeight() / 2 + MARGIN * i, getWidth() / 2 + 5, getHeight() / 2 + MARGIN * i, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        invalidate();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }
}
