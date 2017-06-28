package vn.asiantech.internship.exday16;

import android.content.Context;
import android.graphics.Interpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by datbu on 28-06-2017.
 */
class CustomDuration extends Scroller {
    private final int mDuration = 5000;

    CustomDuration(Context context, AccelerateInterpolator accelerateInterpolator) {
        super(context);
    }

    public CustomDuration(Context context, Interpolator interpolator) {
        super(context, (android.view.animation.Interpolator) interpolator);
    }

    public CustomDuration(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, (android.view.animation.Interpolator) interpolator, flywheel);
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
