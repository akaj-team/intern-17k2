package vn.asiantech.internship.ui.feed;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class ImageAdapter extends PagerAdapter {
    private List<Bitmap> mImages;

    public ImageAdapter(List<Bitmap> images) {
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.item_list_image, container, false);
        ImageView imgPost = (ImageView) view.findViewById(R.id.imgPost);
        imgPost.setImageBitmap(mImages.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
