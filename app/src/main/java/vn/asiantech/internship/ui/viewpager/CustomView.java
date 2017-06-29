package vn.asiantech.internship.ui.viewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * custom tablayout
 * <p>
 * Created by Hai on 6/28/2017.
 */
public class CustomView extends View {
    private boolean mIsSelected;
    private Paint mPaint = new Paint();

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
            float x = getWidth() / 2;
            canvas.drawCircle(x, getHeight(), getHeight(), mPaint);
            canvas.drawLine(0, getHeight(), getWidth() / 2 - getHeight(), getHeight(), mPaint);
            canvas.drawLine(getWidth() / 2 + getHeight(), getHeight(), getWidth(), getHeight(), mPaint);
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
