package vn.asiantech.internship.ui.tablayout.transformer;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

/**
 * BaseTransformer created by GitHub
 */
abstract class BaseTransformer implements PageTransformer {

    /**
     * Called each {@link #transformPage(android.view.View, float)}.
     *
     * @param view is a Fragment
     * @param position is a position of an other thing that i don't know
     */
    protected abstract void onTransform(View view, float position);

    @Override
    public void transformPage(View view, float position) {
        onPreTransform(view, position);
        onTransform(view, position);
    }

    /**
     * Called each {@link #transformPage(android.view.View, float)} before {{@link #onTransform(android.view.View, float)} is called.
     *
     * @param view is a view
     * @param position is a position
     */
    private void onPreTransform(View view, float position) {
        final float width = view.getWidth();

        view.setRotationX(0);
        view.setRotationY(0);
        view.setRotation(0);
        view.setScaleX(1);
        view.setScaleY(1);
        view.setPivotX(0);
        view.setPivotY(0);
        view.setTranslationY(0);
        view.setTranslationX(-width * position);
        view.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
    }
}
