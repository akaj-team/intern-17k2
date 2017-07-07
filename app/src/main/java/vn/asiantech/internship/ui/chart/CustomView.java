package vn.asiantech.internship.ui.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by quanghai on 06/07/2017.
 */

public class CustomView extends View {
    private static final int MARGIN = 30;
    private Paint mPaint;
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
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(5);
        }

        canvas.drawLine(MARGIN, getHeight() / 2, getWidth() - MARGIN, getHeight() / 2, mPaint);
        canvas.drawLine(getWidth() / 2, MARGIN, getWidth() / 2, getHeight() - MARGIN, mPaint);

        //y = 3x^2 - 4x + 1
//        float x1 = getWidth() / 2; float y1 = getHeight() / 2 - 1;
//        float x2 = x1 + 1; float y2 = getHeight() / 2;
//        float x3 = x1 + 1/2; float y3 = getHeight() / 2;
//        canvas.drawPoint(x1, y1, mPaint);
//        canvas.drawPoint(x2, y2, mPaint);
//        canvas.drawPoint(x3, y3, mPaint);
        float xA = 5gi;
        float yA = (3 * xA * xA) - (4 * xA) + 1;
        for(float xB = xA; xB < getWidth();){
            xB = xB + 0.1f;
            float yB = (3 * xB * xB) - (4 * xB) + 1;
            canvas.drawLine(xA, yA, xB, yB, mPaint);
//            canvas.drawPoint(xB, yB, mPaint);
            xA = xB;
            yA = yB;
        }
    }
}
