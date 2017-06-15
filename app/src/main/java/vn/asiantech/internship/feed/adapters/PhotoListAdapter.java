package vn.asiantech.internship.feed.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class PhotoListAdapter extends PagerAdapter {
    private int[] mPhotoList;
    private Context context;

    public PhotoListAdapter(int[] photos, Context context) {
        this.mPhotoList = photos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mPhotoList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_images, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgPhoto);
        imageView.setImageResource(mPhotoList[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }
}
