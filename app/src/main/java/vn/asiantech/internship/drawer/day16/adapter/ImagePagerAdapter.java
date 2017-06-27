package vn.asiantech.internship.drawer.day16.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 26/06/2017.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private List<Integer> mImages;

    @Override
    public int getCount() {
        return mImages.size();
    }

    public ImagePagerAdapter(List<Integer> images) {
        mImages = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View imageLayout = inflater.inflate(R.layout.item_list_image, container, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgFeed);
        imageView.setImageResource(mImages.get(position));
        container.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }


}
