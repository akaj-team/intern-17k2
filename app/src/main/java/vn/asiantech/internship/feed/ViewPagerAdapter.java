package vn.asiantech.internship.feed;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_viewpager, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgViewPager);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(mImageArray[position]);
//        ImageView imgArrowLeft = (ImageView) view.findViewById(R.id.imgArrowLeft);
//        ImageView imgArrowRight = (ImageView) view.findViewById(R.id.imgArrowRight);
//        imgArrowLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(container.getContext(), "AAAAAAAAAAAAAA", Toast.LENGTH_SHORT).show();
//            }
//        });
//        imgArrowRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(container.getContext(), "BBBBBBBBBBBBBB", Toast.LENGTH_SHORT).show();
//            }
//        });
        container.addView(imageView);
//        container.addView(imgArrowLeft);
//        container.addView(imgArrowRight);
        return imageView;
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
