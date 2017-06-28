package vn.asiantech.internship.others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * Canvas
 * Created by AnhHuy on 28-Jun-17.
 */
public class CustomTab extends View {
    private int mWidth;
    private int mHeight;
    private boolean mChoose;
    private Paint mPaint;

    public CustomTab(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public CustomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = mWidth / 3;
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.BLUE);
        if (mChoose) {
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorTransparent));
            mPaint.setColor(Color.BLUE);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2 + 10, getWidth() / 2, mPaint);
            canvas.drawLine(0, getHeight(), mWidth / 2 - getEmptySpace(), getHeight(), mPaint);
            canvas.drawLine(mWidth / 2 + getEmptySpace(), getHeight(), mWidth, getHeight(), mPaint);
        } else {
            canvas.drawLine(0, getHeight(), mWidth, getHeight(), mPaint);
        }
    }

    private float getEmptySpace() {
        return (float) Math.sqrt(mWidth * mWidth / 4 - (mWidth / 2 + 20 - mHeight) * (mWidth / 2 + 20 - mHeight));
    }

    public void setSelected(boolean selected) {
        this.mChoose = selected;
        invalidate();
    }
}
