package vn.asiantech.internship.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 *
 * Created by Hai on 6/15/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageArray;

    public ViewPagerAdapter(Context context) {
    }

    public ViewPagerAdapter(Context context, int[] imageArray) {
        mContext = context;
        mImageArray = imageArray;
    }


    @Override
    public int getCount() {
        return mImageArray.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mImageArray[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
