package vn.asiantech.internship.ui.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Thanh Thien on 7/7/2017.
 * CustomCanvas
 */
public class CustomCanvas extends View {

    private static final String TAG = "CustomCanvas";
    private static final int MARGIN = 50;
    private static final int ARROW_HEIGHT = 20;
    private static int ONE_LINE = 3 * MARGIN;

    private boolean[] mCanScales = new boolean[2];
    private float[] mDifferentPoints = new float[2];
    private float[] mOldPoints = new float[2];
    private int[] mValues = new int[2];
    private Paint[] mPaints = new Paint[5];
    private boolean mNotFirstTouch;
    private int mScale;

    public CustomCanvas(Context context) {
        this(context, null);
    }

    public CustomCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        int[] colors = {Color.BLACK, Color.BLACK, Color.RED, Color.GREEN};
        int[] strokeWidths = {7, 4, 5, 3};
        int[] textSizes = {30, 0, 20, 0};

        for (int i = 0; i < 4; i++) {
            Paint paint = new Paint();
            paint.setColor(colors[i]);
            paint.setStrokeWidth(strokeWidths[i]);
            paint.setTextSize(textSizes[i]);
            if (i == 1 || i == 3) {
                paint.setStrokeCap(Paint.Cap.ROUND);
                paint.setStyle(Paint.Style.STROKE);
            }
            mPaints[i] = paint;
        }

        mCanScales[0] = true;
        mCanScales[1] = true;
        mDifferentPoints[0] = 0;
        mDifferentPoints[1] = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateSystem(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            moveAction(event.getX(), event.getY());
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            upAction();
        }
        return true;
    }

    private void upAction() {
        mNotFirstTouch = false;
    }

    private void moveAction(float x, float y) {
        if (!mNotFirstTouch) {
            mNotFirstTouch = true;
            mOldPoints[0] = x;
            mOldPoints[1] = y;
        } else {
            if (mCanScales[1] && x < mOldPoints[0]) {
                if (ONE_LINE < getWidth() - 5) {
                    setScale(false);
                } else {
                    ONE_LINE = getWidth() - 5;
                }
            } else if (mCanScales[0] && x > mOldPoints[0]) {
                if (ONE_LINE > 25) {
                    setScale(true);
                } else {
                    ONE_LINE = 25;
                }
            }
            if (ONE_LINE > getWidth() - 5) {
                ONE_LINE = getWidth() - 5;
            }

            if (ONE_LINE < 50) {
                ONE_LINE = 50;
            }
            invalidate();
        }
    }

    private void drawCoordinateSystem(Canvas canvas) {
        float positionY = getHeight() * 0.75f + mDifferentPoints[1];
        float positionX = getWidth() * 0.25f + mDifferentPoints[0];
        if (ONE_LINE >= 40 && ONE_LINE <= getWidth()) {
            if (ONE_LINE <= 40) {
                mCanScales[0] = false;
                mCanScales[1] = true;
            }
            if (ONE_LINE >= getWidth()) {
                mCanScales[1] = false;
                mCanScales[0] = true;
            }
            ONE_LINE += mScale;
        }

        // Draw Ox and Oy
        canvas.drawLine(MARGIN + mDifferentPoints[0], positionY, getWidth() - MARGIN, positionY, mPaints[0]);
        canvas.drawLine(positionX, MARGIN + mDifferentPoints[1], positionX, getHeight() - MARGIN + mDifferentPoints[1], mPaints[0]);

        // Draw arrows
        canvas.drawLine(getWidth() - MARGIN + mDifferentPoints[0], positionY, getWidth() - MARGIN - ARROW_HEIGHT + mDifferentPoints[0], positionY + ARROW_HEIGHT, mPaints[1]);
        canvas.drawLine(getWidth() - MARGIN + mDifferentPoints[0], positionY, getWidth() - MARGIN - ARROW_HEIGHT + mDifferentPoints[0], positionY - ARROW_HEIGHT, mPaints[1]);
        canvas.drawLine(positionX, MARGIN, positionX + ARROW_HEIGHT, MARGIN + ARROW_HEIGHT + mDifferentPoints[1], mPaints[1]);
        canvas.drawLine(positionX, MARGIN, positionX - ARROW_HEIGHT, MARGIN + ARROW_HEIGHT + mDifferentPoints[1], mPaints[1]);

        // Draw O, X and Y text
        canvas.drawText("X", getWidth() - MARGIN + mDifferentPoints[0], positionY + ARROW_HEIGHT * 2.2f, mPaints[0]);
        canvas.drawText("Y", positionX - ARROW_HEIGHT * 2.2f, MARGIN, mPaints[0]);
        canvas.drawText("O", positionX - ARROW_HEIGHT * 2.2f, positionY + ARROW_HEIGHT * 2.2f, mPaints[0]);
        canvas.drawCircle(positionX, positionY, 10, mPaints[0]);

        // Draw position
        drawPosition(positionX, positionY, canvas);
        drawParabola(canvas, positionX, positionY, 3, -4, 1);
    }

    // TODO call this method in @onDraw if you want to see parabola follow sin
    private void drawParabola(Canvas canvas) {
        float lengthJump = MARGIN * 3;
        float startPositionX = MARGIN * 2;
        float startPositionY = lengthJump + 4 * MARGIN;
        float endPositionY = getHeight() * 0.95f;
        canvas.drawArc(new RectF(startPositionX, startPositionY, startPositionX + lengthJump, endPositionY), 180, 180, false, mPaints[3]);
        canvas.drawArc(new RectF(startPositionX + lengthJump, startPositionY, startPositionX + lengthJump * 2, endPositionY), 180, -180, false, mPaints[3]);
        canvas.drawArc(new RectF(startPositionX + lengthJump * 2, startPositionY, startPositionX + lengthJump * 3, endPositionY), 180, 180, false, mPaints[3]);
        canvas.drawArc(new RectF(startPositionX + lengthJump * 3, startPositionY, startPositionX + lengthJump * 4, endPositionY), 180, -180, false, mPaints[3]);
    }

    private void drawPosition(float positionX, float positionY, Canvas canvas) {
        mValues[0] = (int) -positionX / ONE_LINE;
        mValues[1] = (int) -((getHeight() - MARGIN) - positionY) / ONE_LINE;
        drawPositionX(canvas, positionX, positionY, mValues[0]);
        drawPositionY(canvas, positionX, positionY, mValues[1]);
    }

    private void drawPositionY(Canvas canvas, float positionX, float positionY, float value) {
        if (getHeight() - (ONE_LINE * value) <= MARGIN * 1.5) {
            return;
        }
        float y;
        if (value < 0) {
            y = positionY - ONE_LINE * value;
        } else if (value == 0) {
            value++;
            drawPositionY(canvas, positionX, positionY, value);
            return;
        } else {
            y = positionY - ONE_LINE * value;
        }
        canvas.drawCircle(positionX, y, 7 + (mScale * 0.05f), mPaints[0]);
        canvas.drawText(value + "", positionX - ARROW_HEIGHT * 1.5f, y, mPaints[2]);
        value++;
        try {
            drawPositionY(canvas, positionX, positionY, value);
        } catch (StackOverflowError e) {
            Log.d(TAG, "drawPositionX: " + e);
        }
    }

    private void drawPositionX(Canvas canvas, float positionX, float positionY, float value) {
        if (ONE_LINE * value >= getWidth() - (MARGIN * 1.5)) {
            return;
        }
        float x;
        if (value < 0) {
            x = positionX + ONE_LINE * value;
        } else if (value == 0) {
            value++;
            drawPositionX(canvas, positionX, positionY, value);
            return;
        } else {
            x = positionX + ONE_LINE * value;
        }
        canvas.drawCircle(x, positionY, 7 + (mScale * 0.05f), mPaints[0]);
        canvas.drawText(value + "", x, positionY + ARROW_HEIGHT * 1.5f, mPaints[2]);
        value++;
        try {
            drawPositionX(canvas, positionX, positionY, value);
        } catch (StackOverflowError e) {
            Log.d(TAG, "drawPositionX: " + e);
        }
    }

    private void drawParabola(Canvas canvas, float positionX, float positionY, float a, float b, float c) {
        boolean addJump = false;
        float start = findPeakOfParabola(a, b, c);
        float end = MARGIN + ARROW_HEIGHT;
        float jump = 0.01f;
        float i;
        float x1;
        float x2;
        float delta;
        float nextY;
        float nextX1;
        float nextX2;
        float nextDelta;
        float positionNextY;

        for (i = start; (positionY - (i * ONE_LINE)) > end; i += jump) {

            // Location of x1, x2
            delta = b * b - (4 * a * (c - i));
            x1 = (float) (((-b) + Math.sqrt(delta)) / (2 * a));
            x2 = (float) (((-b) - Math.sqrt(delta)) / (2 * a));

            // Find location of next x1, x2
            nextY = i + jump;
            nextDelta = b * b - (4 * a * (c - nextY));
            nextX1 = (float) (((-b) + Math.sqrt(nextDelta)) / (2 * a));

            // Set addJump when parabola is not need a little jump
            if ((nextX1 - x1) < 0.009) {
                addJump = true;
            }

            // Jump elastic
            if (addJump) {
                jump += Math.abs((0.1 + Math.abs(x1 - nextX1)) - (nextX1 - x1));
            }

            // Find the next point and connect with the previous (@x1, @x2)
            nextY = i + jump;
            positionNextY = positionY - (nextY * ONE_LINE);
            nextDelta = b * b - (4 * a * (c - nextY));
            nextX1 = (float) (((-b) + Math.sqrt(nextDelta)) / (2 * a));
            nextX2 = (float) (((-b) - Math.sqrt(nextDelta)) / (2 * a));

            // Check position of next location when it go to outside system
            if (positionNextY < end) {
                positionNextY = end + ARROW_HEIGHT;
                nextY = -(positionNextY - positionY) / ONE_LINE;
                nextDelta = b * b - (4 * a * (c - nextY));
                nextX1 = (float) (((-b) + Math.sqrt(nextDelta)) / (2 * a));
                nextX2 = (float) (((-b) - Math.sqrt(nextDelta)) / (2 * a));
                canvas.drawLine(positionX + (x1 * ONE_LINE), positionY - (i * ONE_LINE), positionX + (nextX1 * ONE_LINE), positionNextY, mPaints[3]);
                canvas.drawLine(positionX + (x2 * ONE_LINE), positionY - (i * ONE_LINE), positionX + (nextX2 * ONE_LINE), positionNextY, mPaints[3]);
                break;
            }
            canvas.drawLine(positionX + (x1 * ONE_LINE), positionY - (i * ONE_LINE), positionX + (nextX1 * ONE_LINE), positionNextY, mPaints[3]);
            canvas.drawLine(positionX + (x2 * ONE_LINE), positionY - (i * ONE_LINE), positionX + (nextX2 * ONE_LINE), positionNextY, mPaints[3]);
        }
    }

    private float findPeakOfParabola(float a, float b, float c) {
        float x = -b / (2 * a);
        return (a * x * x) + (b * x) + c;
    }

    private void setScale(boolean isZoomOut) {
        if (!isZoomOut && mCanScales[1]) {
            mScale += 5;
            mCanScales[0] = true;
        } else if (isZoomOut && mCanScales[0]) {
            mScale -= 5;
            mCanScales[1] = true;
        }
    }
}
