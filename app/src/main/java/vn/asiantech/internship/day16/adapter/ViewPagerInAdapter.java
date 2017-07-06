package vn.asiantech.internship.day16.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 26/06/2017.
 */
public class ViewPagerInAdapter extends PagerAdapter {
    private int[] mImages;
    private Context mContext;

    public ViewPagerInAdapter(int[] images, Context context) {
        mImages = images;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_viewpager_tab2, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.imgViewPager);
        imageView.setImageResource(mImages[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
