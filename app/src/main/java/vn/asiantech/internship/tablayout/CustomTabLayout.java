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
public class CustomTabLayout extends View {
    private Paint mFillPaint;
    private Paint mStrokePaint;
    private boolean mIsSelected;

    public CustomTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFillPaint();
        initStrokePaint();
    }

    public CustomTabLayout(Context cxt) {
        super(cxt);
    }

    private void initFillPaint() {
        mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(Color.WHITE);
    }

    private void initStrokePaint() {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStrokeWidth(4);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(Color.DKGRAY);
    }

    public void onDraw(Canvas canvas) {
        if (mIsSelected) {
            RectF rectFFill = new RectF(0, 0, getWidth(), getHeight() * 2);
            RectF rectFStroke = new RectF(1, 1, getWidth() - 1, getHeight() * 2);
            canvas.drawArc(rectFFill, 0, -180, false, mFillPaint);
            canvas.drawArc(rectFStroke, 0, -180, false, mStrokePaint);
        } else {
            canvas.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2, mStrokePaint);
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
