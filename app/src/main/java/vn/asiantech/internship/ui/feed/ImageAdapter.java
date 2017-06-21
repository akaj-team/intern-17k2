package vn.asiantech.internship.ui.feed;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class ImageAdapter extends PagerAdapter implements View.OnClickListener {
    private List<String> mImages;
    private OnClickArrowListener mOnClickArrowListener;

    public ImageAdapter(String images, OnClickArrowListener onClickArrowListener) {
        String[] links = images.split(",");
        Log.i("Tag11 links", links.length + "");
        mImages = new ArrayList<>();
        Collections.addAll(mImages, links);
        mOnClickArrowListener = onClickArrowListener;
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
        ImageButton mImgBtnLeft = (ImageButton) view.findViewById(R.id.imgBtnLeft);
        ImageButton mImgBtnRight = (ImageButton) view.findViewById(R.id.imgBtnRight);

        if (position != 0 && position != mImages.size() - 1) {
            mImgBtnLeft.setVisibility(View.VISIBLE);
            mImgBtnRight.setVisibility(View.VISIBLE);
        } else {
            if (position == 0) {
                mImgBtnLeft.setVisibility(View.GONE);
            }
            if (position == mImages.size() - 1) {
                mImgBtnRight.setVisibility(View.GONE);
            }
        }
        mImgBtnLeft.setOnClickListener(this);
        mImgBtnRight.setOnClickListener(this);
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(container.getContext().getResources().getDrawable(R.mipmap.ic_launcher_round)).cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(container.getContext()).defaultDisplayImageOptions(displayImageOptions).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(imageLoaderConfig);
        imgPost.setImageBitmap(imageLoader.loadImageSync(mImages.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnLeft:
                mOnClickArrowListener.onClickLeft();
                break;
            case R.id.imgBtnRight:
                mOnClickArrowListener.onClickRight();
                break;
        }
    }

    /**
     * interface handle click arrow event in viewpager
     */
    interface OnClickArrowListener {
        void onClickLeft();

        void onClickRight();
    }
}
