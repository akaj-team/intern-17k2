package vn.asiantech.internship.bai24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day16.ui.CustomView;

/**
 * Created by at-dinhvo on 07/07/2017.
 */
public class GraphView extends CustomView {

    private Paint mPaint;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(getResources().getColor(R.color.colorBlack));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPivotX(canvas);
        drawPivotY(canvas);
        drawGraph(canvas);
    }

    private void drawGraph(Canvas canvas) {
        // y = 3x^2 - 4x + 1
    }

    private void drawPivotX(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(getWidth() / 2, 40, getWidth() / 2, getHeight() - 40, mPaint);
    }

    private void drawPivotY(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(40, getHeight() / 2, getWidth() - 40, getHeight() / 2, mPaint);
    }
}
