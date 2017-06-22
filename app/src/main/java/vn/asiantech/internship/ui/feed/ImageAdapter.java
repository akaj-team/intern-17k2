package vn.asiantech.internship.ui.feed;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class ImageAdapter extends PagerAdapter implements View.OnClickListener {
    private String[] mImages;
    private OnClickArrowListener mOnClickArrowListener;

    public ImageAdapter(String images, OnClickArrowListener onClickArrowListener) {
        mImages = images.split(",");
        mOnClickArrowListener = onClickArrowListener;
    }

    @Override
    public int getCount() {
        return mImages.length;
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

        if (position != 0 && position != mImages.length - 1) {
            mImgBtnLeft.setVisibility(View.VISIBLE);
            mImgBtnRight.setVisibility(View.VISIBLE);
        } else {
            if (position == 0) {
                mImgBtnLeft.setVisibility(View.GONE);
            }
            if (position == mImages.length - 1) {
                mImgBtnRight.setVisibility(View.GONE);
            }
        }
        mImgBtnLeft.setOnClickListener(this);
        mImgBtnRight.setOnClickListener(this);
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(container.getContext()).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(imageLoaderConfig);
        imageLoader.displayImage(mImages[position], imgPost);
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
                mOnClickArrowListener.onLeftClick();
                break;
            case R.id.imgBtnRight:
                mOnClickArrowListener.onRightClick();
                break;
        }
    }

    /**
     * interface handle click arrow event in viewpager
     */
    interface OnClickArrowListener {
        void onLeftClick();

        void onRightClick();
    }
}
