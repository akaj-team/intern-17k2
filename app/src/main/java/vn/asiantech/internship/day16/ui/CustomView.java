package vn.asiantech.internship.day16.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
        mPaint.setColor(Color.BLACK);
    }

    public void onDraw(final Canvas canvas) {
        if (mIsSelected) {
            drawTab(canvas);
        } else {
            drawLine(canvas);
        }
    }

    private void drawTab(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorTab));
        RectF rectF = new RectF(-10, getHeight() / 2, getWidth() + 10, 2 * getHeight());
        canvas.drawArc(rectF, 180, 180, false, mPaint);
    }

    private void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(10);
        mPaint.setColor(getResources().getColor(R.color.colorTab));
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
