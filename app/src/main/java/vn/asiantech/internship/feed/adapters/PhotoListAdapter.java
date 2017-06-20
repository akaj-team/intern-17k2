package vn.asiantech.internship.feed.adapters;

import android.content.Context;
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
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class PhotoListAdapter extends PagerAdapter {

    public static final String KEY_IMAGE = "Image";

    private String[] mPhotoList;
    private Context mContext;
    private ImageLoader mImageLoader;

    public PhotoListAdapter(Context context, String[] photos) {
        this.mPhotoList = photos;
        this.mContext = context;
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(context.getResources().getDrawable(R.mipmap.ic_launcher_round)).cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions).build();
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(imageLoaderConfig);
    }

    public void setPhotoList(String[] photos) {
        this.mPhotoList = photos;
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
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_photo, container, false);
        ImageView imgPicture = (ImageView) view.findViewById(R.id.imgPhoto);
        mImageLoader.displayImage(mPhotoList[position].trim(), imgPicture);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }
}
