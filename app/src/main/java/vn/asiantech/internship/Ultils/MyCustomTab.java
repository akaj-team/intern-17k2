package vn.asiantech.internship.Ultils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class to draw top part Tab of TabLayout
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/27/2017
 */
public class MyCustomTab extends View {

    private int mWidth;
    private int mHeight;
    private boolean mSelected;
    private Paint mPaint;

    public MyCustomTab(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyCustomTab(Context context, @Nullable AttributeSet attrs) {
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
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
        if (mSelected) {
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2 + 10, getWidth() / 2, mPaint);
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth() / 2, getWidth() / 2 + 10, getWidth() / 2, mPaint);
            canvas.drawLine(0, getHeight(), mWidth / 2 - getDxEmpty(), getHeight(), mPaint);
            canvas.drawLine(mWidth / 2 + getDxEmpty(), getHeight(), mWidth, getHeight(), mPaint);
        } else {
            canvas.drawLine(0, getHeight(), mWidth, getHeight(), mPaint);
        }
    }

    private float getDxEmpty() {
        return (float) Math.sqrt(1.0 * mWidth * mWidth / 4 - (1.0 * mWidth / 2 + 10 - 1.0 * mHeight) * (1.0 * mWidth / 2 + 10 - 1.0 * mHeight));
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
        invalidate();
    }
}
