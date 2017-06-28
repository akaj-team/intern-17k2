package vn.asiantech.internship.others;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Duration scroll tab layout.
 * Created by AnhHuy on 27-Jun-17.
 */
public class SpeedScroll extends Scroller {
    private final int mDuration = 5000;

    public SpeedScroll(Context context) {
        super(context);
    }

    public SpeedScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public SpeedScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
