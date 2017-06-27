package vn.asiantech.internship.ui.tablayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * Created by luiss on 6/16/2017.
 * GridView Clock
 */
public class GridView extends View  {

    private Paint mPaintLine;
    private Paint mPaintCircle;
    private Paint mPaintCircleFill;
    private int[] mDrawable = {
            R.drawable.vector_love,
            R.drawable.vector_star,
            R.drawable.vector_important,
            R.drawable.vector_security,
            R.drawable.vector_settings_black
    };

    private int[] mDrawableSelected = {
            R.drawable.vector_love_selected,
            R.drawable.vector_star_selected,
            R.drawable.vector_important_selected,
            R.drawable.vector_security_selected,
            R.drawable.vector_settings_black_selected
    };

    private int mTabSelected;

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStyle(canvas, mTabSelected);

        // Draw Icon and selected icon
        drawIcon(canvas, mTabSelected);
    }

    private void drawIcon(Canvas canvas, int position) {
        Bitmap bitmap;
        for (int i = 0; i < 5; i++) {
            if (i != position) {
                bitmap = BitmapFactory.decodeResource(getResources(), mDrawable[i]);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), mDrawableSelected[i]);
            }
            int lengthOfOne = 2 * (getWidth() / 2) / 5;
            float left = lengthOfOne * (i + 1) - lengthOfOne / 2 - bitmap.getWidth() / 2;
            float top = (getHeight() - bitmap.getHeight()) / 2.0f;
            RectF dst = new RectF(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
            canvas.drawBitmap(bitmap, null, dst, mPaintLine);
        }
    }

    private void drawStyle(Canvas canvas, int position) {
        position += 1;
        int centerViewX = getWidth() / 2;

        int lengthOfOne = 2 * centerViewX / 5;
        int positionY = 20;

        canvas.drawLine(0, positionY, lengthOfOne * position - lengthOfOne, positionY, mPaintLine);
        canvas.drawLine(lengthOfOne * position, positionY, getWidth(), positionY, mPaintLine);
        canvas.drawArc(new RectF(lengthOfOne * position - lengthOfOne, 2, lengthOfOne * position, getHeight() / 3), 180, 178, false, mPaintCircleFill);
        canvas.drawArc(new RectF(lengthOfOne * position - lengthOfOne, 2, lengthOfOne * position, getHeight() / 3), 180, 178, false, mPaintCircle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void init() {
        mTabSelected = 2;

        mPaintLine = new Paint();
        mPaintLine.setColor(0xffBFBDC4);
        mPaintLine.setStrokeWidth(3);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(0xffBFBDC4);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setStrokeWidth(3);

        mPaintCircleFill = new Paint();
        mPaintCircleFill.setColor(0xfff4f4f4);
        mPaintCircleFill.setStyle(Paint.Style.FILL);
        mPaintCircleFill.setStrokeWidth(3);
    }
}
