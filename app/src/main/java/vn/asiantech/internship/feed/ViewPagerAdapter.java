package vn.asiantech.internship.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mSampleImages;

    ViewPagerAdapter(Context context, int[] sampleImages) {
        mSampleImages = sampleImages;
        mContext = context;
    }

    void setImage(int[] SampleImages) {
        mSampleImages = SampleImages;
    }

    @Override
    public int getCount() {
        return mSampleImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_feed, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgSlide);
        imageView.setImageResource(mSampleImages[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}
