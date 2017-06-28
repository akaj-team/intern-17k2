package vn.asiantech.internship.day9.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.asiantech.internship.R;

/**
 * Created by rimoka on 15/06/2017.
 */
public class CustomFeedPagerAdapter extends PagerAdapter {
    private int[] mImages;
    private Context mContext;

    public CustomFeedPagerAdapter(int[] images, Context context) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_pager, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imgFeed);
        imageView.setImageResource(mImages[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
