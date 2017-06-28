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
public class MyTabView extends View {

    private Paint[] mPaint;
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

    int mLengthOfOne;
    private float mTabSelected;
    private float mOldCLicked, mNewClicked;
    private boolean isTouch;

    private OnGridViewListener mOnGridViewListener;

    public MyTabView(Context context) {
        super(context);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mOldCLicked = getWidth() / 2; //

        // Draw Icon and selected icon
        drawIcon(canvas, mTabSelected);

        drawStyle(canvas, mTabSelected);
        if (isTouch) {
            drawTouchStyle(canvas, mTabSelected);
        }
    }

    private void drawIcon(Canvas canvas, float position) {
        Bitmap bitmap;
        for (int i = 0; i < 5; i++) {
            if (i != position) {
                bitmap = BitmapFactory.decodeResource(getResources(), mDrawable[i]);
            } else {
                bitmap = BitmapFactory.decodeResource(getResources(), mDrawableSelected[i]);
            }
            mLengthOfOne = 2 * (getWidth() / 2) / 5;
            float left = mLengthOfOne * (i + 1) - mLengthOfOne / 2 - bitmap.getWidth() / 2;
            float top = (getHeight() - bitmap.getHeight()) / 2.0f;
            RectF dst = new RectF(left, top, left + bitmap.getWidth(), top + bitmap.getHeight());
            canvas.drawBitmap(bitmap, null, dst, mPaint[0]);
        }
    }

    private void drawStyle(Canvas canvas, float position) {
        position += 1;
        int centerViewX = getWidth() / 2;
        int lengthOfOne = 2 * centerViewX / 5;
        int positionY = 25;

        canvas.drawLine(0, positionY, lengthOfOne * position - lengthOfOne, positionY, mPaint[0]);
        canvas.drawLine(lengthOfOne * position, positionY, getWidth(), positionY, mPaint[0]);
        canvas.drawArc(new RectF(lengthOfOne * position - lengthOfOne, 8, lengthOfOne * position, getHeight() / 3), 180, 178, false, mPaint[4]);
        canvas.drawArc(new RectF(lengthOfOne * position - lengthOfOne, 8, lengthOfOne * position, getHeight() / 3), 180, 178, false, mPaint[2]);
    }

    private void drawTouchStyle(Canvas canvas, float position) {
        position += 1;
        int centerViewX = getWidth() / 2;
        int lengthOfOne = 2 * centerViewX / 5;
        int positionY = 22;

        canvas.drawLine(0, positionY, lengthOfOne * position - lengthOfOne, positionY, mPaint[1]);
        canvas.drawLine(lengthOfOne * position, positionY, getWidth(), positionY, mPaint[1]);
        canvas.drawArc(new RectF(lengthOfOne * position - lengthOfOne, 5, lengthOfOne * position, getHeight() / 3), 180, 178, false, mPaint[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // Move action having some problem when using inside an other Viewpager =))
            isTouch = true;
            mNewClicked = event.getX();
            if (mNewClicked > mOldCLicked) {
                mTabSelected += 0.1f;
            } else {
                mTabSelected -= 0.1f;
            }
            mOldCLicked = mNewClicked;
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouch = true;
            mNewClicked = event.getX();
            if (mNewClicked > mOldCLicked) {
                mTabSelected += 0.2f;
            } else {
                mTabSelected -= 0.2f;
            }
            mOldCLicked = mNewClicked;
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isTouch = false;
            mTabSelected = getPositionClicked(mNewClicked);
            mOnGridViewListener.onClickItem(mTabSelected);
            invalidate();
            return true;
        } else {
            return true;
        }
    }

    public Float getPositionClicked(float newClicked) {
        int maxWidth = getWidth();
        int lengthOfOne = maxWidth / 5;
        if (newClicked >= 0 && newClicked < lengthOfOne) {
            return 0f;
        } else if (lengthOfOne >= 0 && newClicked < 2 * lengthOfOne) {
            return 1f;
        } else if (2 * lengthOfOne >= 0 && newClicked < 3 * lengthOfOne) {
            return 2f;
        } else if (3 * lengthOfOne >= 0 && newClicked < 4 * lengthOfOne) {
            return 3f;
        } else {
            return 4f;
        }
    }

    private void init() {
        mPaint = new Paint[5];
        mTabSelected = 2;

        int[] colors = {0xffBFBDC4, 0xffFC0095, 0xffBFBDC4, 0xffFC0095, 0xfff4f4f4};
        int[] strokeWidth = {3, 5};
        Paint.Style[] paintStyle = {Paint.Style.STROKE, Paint.Style.FILL};

        for (int i = 0; i < 5; i++) {
            Paint paint = new Paint();
            paint.setColor(colors[i]);
            paint.setStrokeWidth(strokeWidth[(i == 3) ? 1 : 0]);
            paint.setStyle(paintStyle[(i == 4) ? 1 : 0]);
            mPaint[i] = paint;
        }
    }

    public void setTabSelected(float position) {
        mTabSelected = position;
        invalidate();
    }

    public void setOnTouch(boolean b) {
        isTouch = b;
    }

    public void onClickItem(OnGridViewListener onGridViewListener) {
        mOnGridViewListener = onGridViewListener;
    }

    /**
     * OnGridViewListener
     */
    interface OnGridViewListener {
        void onClickItem(float position);
    }
}
