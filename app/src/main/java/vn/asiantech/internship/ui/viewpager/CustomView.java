package vn.asiantech.internship.ui.viewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Custom tablayout
 * <p>
 * Created by Hai on 6/28/2017.
 */
public class CustomView extends View {
    private boolean mIsSelected;
    private Paint mPaint = new Paint();
    private RectF mRectF = new RectF();

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
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        if (mIsSelected) {
            mRectF.left = getLeft();
            mRectF.top = getTop();
            mRectF.right = getRight();
            mRectF.bottom = getBottom() * 2;
            canvas.drawArc(mRectF, 180, 180, false, mPaint);
        } else {
            Log.d("xxx", "onDraw: ");
            canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
        }
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
        invalidate();
    }
}
