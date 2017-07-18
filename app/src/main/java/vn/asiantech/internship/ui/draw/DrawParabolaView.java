package vn.asiantech.internship.ui.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ducle on 11/07/2017.
 * DrawParabolaView is view draw parabola
 */
public class DrawParabolaView extends View {
    private static final String TAG = DrawParabolaView.class.getSimpleName();
    private Paint mPaint;
    private Path mPath;
    private float mOriginX = 0;
    private float mOriginY = 0;
    public boolean mMove = false;

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
//        if (mMove) {
        canvas.translate(mOriginX, mOriginY);
        Log.d(TAG, "onDraw: " + mOriginX + " " + mOriginY);
//        }
        float k = getWidth() / 2 / 5;
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
}
