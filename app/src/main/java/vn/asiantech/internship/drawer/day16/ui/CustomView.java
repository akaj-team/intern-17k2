package vn.asiantech.internship.drawer.day16.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 28/06/2017.
 */
public class CustomView extends View {

    private Paint mPaint;
    private boolean mIsSelected;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
    }

    public void onDraw(final Canvas canvas) {
        if (mIsSelected) {
            drawTab(canvas);
        } else {
            drawLine(canvas);
        }
    }

    private void drawTab(Canvas canvas) {
        mPaint.setStrokeWidth(7);
        canvas.drawLine(0, 0, getWidth(), 0, mPaint);
        canvas.drawLine(0, getHeight(), 0, 0, mPaint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(7);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
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
