package vn.asiantech.internship.day15.drawer.ui.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 15/06/2017.
 */
class FeedPagerAdapter extends PagerAdapter {

    private String[] mImageItems;
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDefaultOptions;

    FeedPagerAdapter(Context context, String[] imageItems) {
        mContext = context;
        mImageItems = imageItems;
        configImage();
    }

    @Override
    public int getCount() {
        return mImageItems.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View imageLayout = inflater.inflate(R.layout.item_list_image, container, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgFeed);
        loadImage(mImageItems[position], imageView);
        container.addView(imageLayout);
        return imageLayout;
    }

    private void configImage() {
        mDefaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext.getApplicationContext())
                .defaultDisplayImageOptions(mDefaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }

    private void loadImage(String link, ImageView imageView) {
        mImageLoader.displayImage(link, imageView, mDefaultOptions);
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
