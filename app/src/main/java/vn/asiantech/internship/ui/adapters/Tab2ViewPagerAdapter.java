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
    private final int[] mResource;

    public Tab2ViewPagerAdapter(Context context, int[] resource) {
        mContext = context;
        mResource = resource;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_pager_tab2, container, false);
        ImageView imgImageTab2 = (ImageView) view.findViewById(R.id.imgTab2);
        imgImageTab2.setImageResource(mResource[position]);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mResource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
