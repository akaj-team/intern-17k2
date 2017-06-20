package vn.asiantech.internship.feed;

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
    private int[] mImageArray;

    ViewPagerAdapter(int[] imageArray) {
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
        imageView.setImageResource(mImageArray[position]);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(container.getContext()));
//        ImageView imgArrowLeft = (ImageView) view.findViewById(R.id.imgArrowLeft);
//        ImageView imgArrowRight = (ImageView) view.findViewById(R.id.imgArrowRight);
//        if (mImageArray.length > 1 && position < mImageArray.length - 1) {
//            imgArrowRight.setVisibility(View.VISIBLE);
//            imgArrowRight.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageView.setImageResource(mImageArray[position + 1]);
//                }
//            });
//        } else {
//            imgArrowRight.setVisibility(View.INVISIBLE);
//        }
//        if (mImageArray.length > 1 && position > mImageArray[0]) {
//            imgArrowLeft.setVisibility(View.VISIBLE);
//            imgArrowRight.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageView.setImageResource(mImageArray[position - 1]);
//                }
//            });
//        } else {
//            imgArrowLeft.setVisibility(View.INVISIBLE);
//        }
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
