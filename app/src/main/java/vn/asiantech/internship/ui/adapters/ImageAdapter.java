package vn.asiantech.internship.ui.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import vn.asiantech.internship.R;

/**
 * Adapter for Image View Pager
 * Created by huypham on 15/06/2017.
 * Change on 20/06/2017 add Image Loader to load image from database
 */
class ImageAdapter extends PagerAdapter {
    private final Context mContext;
    private String[] mImageList;
    private ImageLoader mImageLoader;

    ImageAdapter(Context context, String[] imageList) {
        mContext = context;
        mImageList = imageList;
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(ContextCompat.getDrawable(context, R.drawable.bg_cat))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(configuration);
    }

    @Override
    public int getCount() {
        return mImageList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, container, false);
        ImageView imgFood = (ImageView) view.findViewById(R.id.imgFood);

        mImageLoader.displayImage(mImageList[position].trim(), imgFood);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
