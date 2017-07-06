package vn.asiantech.internship.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Adapter for tab layout second.
 * Created by AnhHuy on 27-Jun-17.
 */
public class Tab2ViewPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final int[] mResources;

    public Tab2ViewPagerAdapter(Context context, int[] resources) {
        mContext = context;
        mResources = resources;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_pager_tab2, container, false);
        ImageView imgTab2 = (ImageView) view.findViewById(R.id.imgTab2);
        imgTab2.setImageResource(mResources[position]);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
