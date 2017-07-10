package vn.asiantech.internship.bai24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
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
        mPaint.setStrokeWidth(2);
        Point p = fx(3, -4, 1, 0);
        for (int i = 1; i < getWidth(); i++) {
            solveCoordinatorXY(i, canvas);
        }
    }

    private Point fx(float a, float b, float c, int x) {
        Point point = new Point();
        float pX = (x - getWidth() / 2) / 50;
        double pY = a * pX * pX + b * pX + c;
        double y = (getHeight() / 2 - pY * 50);
        point.x = x;
        point.y = (int) y;
        return point;
    }

    private void solveCoordinatorXY(int x, Canvas canvas) {
        Point oldPoint = fx(3, -4, 1, 0);
        Point newPoint;
        for (int i = 1; i < getWidth(); i++) {
            newPoint = fx(3, -4, 1, i);
            canvas.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y, mPaint);
            oldPoint = newPoint;
        }
    }

    private void drawPivotX(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(getWidth() / 2, 40, getWidth() / 2, getHeight() - 40, mPaint);
        mPaint.setStrokeWidth(6);
        for (double i = (-getWidth() / 2) / 50; i <= (getWidth() / 2) / 50; i++) {
            canvas.drawPoint((float) (getWidth() / 2 + i * 50), getHeight() / 2, mPaint);
        }
    }

    private void drawPivotY(Canvas canvas) {
        mPaint.setStrokeWidth(3);
        canvas.drawLine(40, getHeight() / 2, getWidth() - 40, getHeight() / 2, mPaint);
        mPaint.setStrokeWidth(6);
        for (double i = (-getHeight() / 2) / 50; i <= (getHeight() / 2) / 50; i++) {
            canvas.drawPoint(getWidth() / 2, (float) (getHeight() / 2 + i * 50), mPaint);
        }
    }
}
