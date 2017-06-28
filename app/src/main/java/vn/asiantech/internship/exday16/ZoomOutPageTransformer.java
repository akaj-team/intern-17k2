package vn.asiantech.internship.exday16;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by datbu on 27-06-2017.
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private boolean mIsPagingEnabled() {
        return false;
    }

    private boolean mHideOffscreenPages() {
        return true;
    }

    public void transformPage(View view, float position) {
        final float width = view.getWidth();

        view.setRotationX(0);
        view.setRotationY(0);
        view.setRotation(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setPivotX(0);
        view.setPivotY(0);
        view.setTranslationY(0);
        view.setTranslationX(mIsPagingEnabled() ? 0f : -width * position);

        if (mHideOffscreenPages()) {
            view.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
        } else {
            view.setAlpha(1f);
        }
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }
}
