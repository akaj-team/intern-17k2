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
    private Paint mPaint2;
    private boolean mIsSelected;

    public CustomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initPaint2();
    }

    public CustomTab(Context cxt) {
        super(cxt);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    private void initPaint2() {
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStrokeWidth(4);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setColor(Color.DKGRAY);
    }

    public void onDraw(Canvas canvas) {
        if (mIsSelected) {
            RectF rectF = new RectF(0, 0, getWidth(), getHeight() * 2);
            RectF rectF2 = new RectF(1, 1, getWidth() - 1, getHeight() * 2);
            canvas.drawArc(rectF, 0, -180, false, mPaint);
            canvas.drawArc(rectF2, 0, -180, false, mPaint2);
        } else {
            canvas.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2, mPaint2);
        }
    }

    @Override
    public boolean isSelected() {
        return mIsSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        mIsSelected = selected;
        invalidate();
    }
}
