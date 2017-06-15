package vn.asiantech.internship.drawer.ui.feed;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
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
    LayoutInflater mInflater;

    public FeedPagerAdapter(Context context, List<Integer> imageItems) {
        mImageItems = imageItems;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImageItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = mInflater.inflate(R.layout.item_list_image, container, false);
//        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgFeed);
        imageView.setImageResource(mImageItems.get(position));
        container.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }
}
