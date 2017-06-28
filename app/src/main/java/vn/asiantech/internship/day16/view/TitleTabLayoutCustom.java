package vn.asiantech.internship.day16.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 26/06/2017.
 */
public class TitleTabLayoutCustom extends android.support.v7.widget.AppCompatTextView {

    public TitleTabLayoutCustom(Context context) {
        super(context);
    }

    public TitleTabLayoutCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public TitleTabLayoutCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RectF rectf = new RectF(0, 0, getWidth(), getHeight());
    }

    private void initPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(30);
    }
}
