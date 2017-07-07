package vn.asiantech.internship.ui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/7/2017.
 */
public class MyCanvas extends View {
    private static final int UNIT = 50;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Path mPath;

    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        mPaint.setColor(Color.BLACK);
        //Ox
        canvas.drawLine(20, mHeight / 2, mWidth - 20, mHeight / 2, mPaint);
        canvas.drawLine(mWidth - 20, mHeight / 2, mWidth - 30, mHeight / 2 - 10, mPaint);
        canvas.drawLine(mWidth - 20, mHeight / 2, mWidth - 30, mHeight / 2 + 10, mPaint);

        //Oy
        canvas.drawLine(mWidth / 2, 20, mWidth / 2, mHeight - 20, mPaint);
        canvas.drawLine(mWidth / 2, 20, mWidth / 2 - 10, 30, mPaint);
        canvas.drawLine(mWidth / 2, 20, mWidth / 2 + 10, 30, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < mWidth / 2 - 20; i += UNIT) {
            canvas.drawPoint(mWidth / 2 + i, mHeight / 2, mPaint);
            canvas.drawPoint(mWidth / 2 - i, mHeight / 2, mPaint);
        }
        for (int i = 0; i < mHeight / 2 - 20; i += UNIT) {
            canvas.drawPoint(mWidth / 2, mHeight / 2 + i, mPaint);
            canvas.drawPoint(mWidth / 2, mHeight / 2 - i, mPaint);
        }

        mPaint.setStrokeWidth(1);
        Point point = fx(3, -4, 1, 20);
        mPath.moveTo(point.x, point.y);

        for (int i = 21; i < mWidth - 20; i++) {
            point = fx(3, -4, 1, i);
            mPath.lineTo(point.x, point.y);
            mPath.moveTo(point.x, point.y);
        }
        canvas.drawPath(mPath, mPaint);
        mPath.close();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(1);
        mPath = new Path();
    }

    //f(x) = ax^2 + bx + c
    private Point fx(float a, float b, float c, int x) {
        Point point = new Point();
        float xOnOxy = (x - mWidth / 2.0f) / UNIT;
        double yOnOxy = a * Math.pow(xOnOxy, 2) + b * xOnOxy + c;
        double y = (mHeight / 2 - yOnOxy * UNIT);
        point.x = x;
        point.y = (int) y;
        return point;
    }
}
