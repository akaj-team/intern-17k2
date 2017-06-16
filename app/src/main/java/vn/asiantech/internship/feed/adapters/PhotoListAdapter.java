package vn.asiantech.internship.feed.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.feed.ShowImageActivity;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class PhotoListAdapter extends PagerAdapter {

    public static final String KEY_IMAGE = "Image";

    private int[] mPhotoList;
    private Context mContext;

    public PhotoListAdapter(Context context, int[] photos) {
        this.mPhotoList = photos;
        this.mContext = context;
    }

    public void setPhotoList(int[] mPhotoList) {
        this.mPhotoList = mPhotoList;
    }

    @Override
    public int getCount() {
        return mPhotoList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_photo, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgPhoto);
        imageView.setImageResource(mPhotoList[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra(KEY_IMAGE, mPhotoList[position]);
                mContext.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }
}
