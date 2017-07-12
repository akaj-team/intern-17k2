package vn.asiantech.internship.ui.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ducle on 11/07/2017.
 * DrawParabolaView is view draw parabola
 */
public class DrawParabolaView extends View {
    private Paint mPaint;
    private Path mPath;

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
        canvas.drawLine(50, getHeight() / 2, getWidth() - 50, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, 50, getWidth() / 2, getHeight() - 50, mPaint);
        mPath.moveTo(getWidth() - 50, getHeight() / 2 - 10);
        mPath.lineTo(getWidth() - 50, getHeight() / 2 + 10);
        mPath.lineTo(getWidth() - 10, getHeight() / 2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(getWidth() / 2 - 10, 50);
        mPath.lineTo(getWidth() / 2 + 10, 50);
        mPath.lineTo(getWidth() / 2, 10);
        canvas.drawPath(mPath, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        float k = getWidth() / 2 / 5;
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

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }
}
