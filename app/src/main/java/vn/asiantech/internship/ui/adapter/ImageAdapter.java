package vn.asiantech.internship.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Adapter for Image View Pager
 * Created by huypham on 15/06/2017.
 */

class ImageAdapter extends PagerAdapter {
    private final Context mContext;
    private int[] mImageLists;

    ImageAdapter(Context context, int[] imageLists) {
        mContext = context;
        mImageLists = imageLists;
    }

    @Override
    public int getCount() {
        return mImageLists.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, container, false);
        ImageView imgFood = (ImageView) view.findViewById(R.id.imgFood);
        imgFood.setImageResource(mImageLists[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
