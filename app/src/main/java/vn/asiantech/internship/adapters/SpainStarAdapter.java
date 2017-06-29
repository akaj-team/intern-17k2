package vn.asiantech.internship.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class SpainStarAdapter extends PagerAdapter {
    private int[] mStars;

    public SpainStarAdapter(int[] stars) {
        this.mStars = stars;
    }

    @Override
    public int getCount() {
        return mStars.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgStar = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_spain_star, container, false);
        imgStar.setImageResource(mStars[position]);
        container.addView(imgStar);
        return imgStar;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
