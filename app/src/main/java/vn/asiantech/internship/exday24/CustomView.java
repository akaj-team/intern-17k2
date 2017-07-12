package vn.asiantech.internship.exday24;

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
 * Created by datbu on 10-07-2017.
 */
public class CustomView extends View {
    private static final int MARGIN = 30;
    private Paint mPaint;
    private Point mPoint;
    private Path mPath = new Path();

    public CustomView(Context context) {
        super(context);
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
        int width = getWidth();
        int height = getHeight();
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setTextSize(30);
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(3);
        }

        int unit = 100;
        float x = width / 2 + 10 * unit;
        float y = height / 2 - (3 * x * x - 4 * x + 1) * unit;
        for (double i = -10; i < 10; i += 0.05f) {
            float x2 = (float) (width / 2 + i * unit);
            float y2 = (float) (height / 2 - (3 * i * i - 4 * i + 1) * unit);
            canvas.drawLine(x, y, x2, y2, mPaint);
            x = x2;
            y = y2;
        }

        canvas.drawLine(MARGIN, height / 2, width - MARGIN, height / 2, mPaint);
        canvas.drawLine(width / 2, MARGIN, width / 2, height - MARGIN, mPaint);
        canvas.drawText("O", width / 2 - 30, height / 2 + 30, mPaint);
        canvas.drawText("x", width - 30, height / 2 + 30, mPaint);
        canvas.drawText("y", width / 2 - 30, 30, mPaint);


//        canvas.drawLine(width / 2, 0, width / 2, height, mPaint);
//        canvas.drawLine(width / 2, 0, width / 2 - 10, 10, mPaint);
//        canvas.drawLine(width / 2, 0, width / 2 + 10, 10, mPaint);
//        canvas.drawLine(width / 2 - 10, 10, width / 2 + 10, 10, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);


        mPath.moveTo(width - MARGIN, height / 2 - 10);
        mPath.lineTo(width, height / 2);
        mPath.lineTo(width - MARGIN, height / 2 + 10);

        mPath.moveTo(width / 2 + 10, MARGIN);
        mPath.lineTo(width / 2, 0);
        mPath.lineTo(width / 2 - 10, MARGIN);
        canvas.drawPath(mPath, mPaint);

    }
}
