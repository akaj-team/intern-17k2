package vn.asiantech.internship.ui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/7/2017.
 */

public class MyCanvas extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public MyCanvas(Context context) {
        this(context, null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();

        Path path = new Path();
        canvas.drawLine(20, mHeight / 2, mWidth - 20, mHeight / 2, mPaint);
        path.moveTo(mWidth - 20, mHeight / 2);
        path.lineTo(mWidth - 30, mHeight / 2 - 10);
        path.moveTo(mWidth - 20, mHeight / 2);
        path.lineTo(mWidth - 30, mHeight / 2 + 10);
        canvas.drawLine(mWidth / 2, 20, mWidth / 2, mHeight - 20, mPaint);
        path.moveTo(mWidth / 2, 20);
        path.lineTo(mWidth / 2 - 10, 30);
        path.moveTo(mWidth / 2, 20);
        path.lineTo(mWidth / 2 + 10, 30);
        canvas.drawPath(path, mPaint);
        Path path1 = new Path();
        mPaint.setColor(Color.RED);
        int i;
        mPaint.setStrokeWidth(3);
        for (i = 0; i < mWidth / 2 - 20; i += 50) {
            canvas.drawPoint(mWidth / 2 + i, mHeight / 2, mPaint);
            canvas.drawPoint(mWidth / 2 - i, mHeight / 2, mPaint);
        }
        for (i = 0; i < mHeight / 2 - 20; i += 50) {
            canvas.drawPoint(mWidth / 2, mHeight / 2 + i, mPaint);
            canvas.drawPoint(mWidth / 2, mHeight / 2 - i, mPaint);
        }
        i = 20;
        mPaint.setStrokeWidth(1);
        Point point = fx(i);
        path1.moveTo(point.x, point.y);
        for (i = 21; i < mWidth - 20; i++) {
            point = fx(i);
            Log.i("tag11", point.x + "--" + point.y);
            path1.lineTo(point.x, point.y);
            path1.moveTo(point.x, point.y);
        }
        canvas.drawPath(path1, mPaint);
        path.close();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
    }

    private Point fx(int x) {
        Point point = new Point();
        float xOnOxy = (x - mWidth * 1.0f / 2) / 50;
        double yOnOxy = 3 * Math.pow(xOnOxy, 2) - 4 * xOnOxy + 1;
        double y = (mHeight / 2 - yOnOxy * 50);
        point.x = x;
        point.y = (int) y;
        return point;
    }
}
