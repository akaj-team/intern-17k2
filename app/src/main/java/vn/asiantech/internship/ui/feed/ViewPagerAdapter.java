package vn.asiantech.internship.ui.feed;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import vn.asiantech.internship.R;

/**
 * adapter of ViewPager
 * <p>
 * Created by Hai on 6/15/2017.
 */
class ViewPagerAdapter extends PagerAdapter {
    private String[] mImageArray;

    ViewPagerAdapter(String[] imageArray) {
        mImageArray = imageArray;
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_viewpager, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imgViewPager);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        final ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(container.getContext()));
        imageLoader.displayImage(mImageArray[position].trim(), imageView);
        container.addView(view);
        return view;
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
