package vn.asiantech.internship.ui.feeds;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by root on 6/15/17.
 */

public class FeedImagesAdapter extends PagerAdapter {
    private List<Integer> mItems;

    public FeedImagesAdapter( List<Integer> items) {
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater mLayoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_image, container, false);
        ImageView imgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);
        imgThumb.setImageResource(mItems.get(position));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
