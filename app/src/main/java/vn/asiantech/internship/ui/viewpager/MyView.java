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
 * Created by Hai on 6/28/2017.
 */

public class MyView extends View {
    private boolean mIsSelected;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        if (mIsSelected) {
            float x = getWidth() / 2;
            canvas.drawCircle(x, x, x, paint);
        } else {
            Log.d("xxx", "onDraw: ");
            canvas.drawLine(0, getHeight(), 0, getWidth(), paint);
        }
    }

    public void setSelected(boolean isSelected) {
        mIsSelected = isSelected;
        invalidate();
    }
}
