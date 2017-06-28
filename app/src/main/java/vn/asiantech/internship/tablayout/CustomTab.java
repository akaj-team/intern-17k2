package vn.asiantech.internship.tablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Used to custom tabLayout
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-28
 */
public class CustomTab extends View {
    private Paint mPaint;

    public CustomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomTab(Context cxt) {
        super(cxt);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        drawCurve(canvas, 0, getWidth() / 5);
        drawCurve(canvas, getWidth() / 5, getWidth() / 5 * 2);
        drawCurve(canvas, getWidth() / 5 * 2, getWidth() / 5 * 3);
        drawCurve(canvas, getWidth() / 5 * 3, getWidth() / 5 * 4);
        drawCurve(canvas, getWidth() / 5 * 4, getWidth() / 5 * 5);
    }

    private void drawCurve(Canvas canvas, int x1, int x2) {
        RectF rectF = new RectF(x1, 0, x2, getHeight() / 2);
        canvas.drawArc(rectF, 0, -180, false, mPaint);
        canvas.drawLine(0, getHeight() / 4, x1, getHeight() / 4, mPaint);
        canvas.drawLine(x2, getHeight() / 4, getWidth(), getHeight() / 4, mPaint);
    }
}
