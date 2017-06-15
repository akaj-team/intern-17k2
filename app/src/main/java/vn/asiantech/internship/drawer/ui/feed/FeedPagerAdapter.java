package vn.asiantech.internship.drawer.ui.feed;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 15/06/2017.
 */

public class FeedPagerAdapter extends PagerAdapter {

    private List<Integer> mImageItems;

    public FeedPagerAdapter(List<Integer> imageItems) {
        mImageItems = imageItems;
    }

    @Override
    public int getCount() {
        return mImageItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View imageLayout = inflater.inflate(R.layout.item_image, container, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgFeed);
        imageView.setImageResource(R.drawable.img_danang);
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
        container.removeView((CardView) object);
    }
}
