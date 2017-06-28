package vn.asiantech.internship.tablayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display images in viewpagerB.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-27
 */
class SmallAdapter extends PagerAdapter {
    private final List<Integer> mImages;
    private final LayoutInflater mInflater;

    SmallAdapter(Context context, List<Integer> images) {
        mImages = images;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = mInflater.inflate(R.layout.item_small_viewpager, container, false);
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgOfSmallViewPager);
        imageView.setImageResource(mImages.get(position));
        container.addView(imageLayout, 0);
        return imageLayout;
    }
}
