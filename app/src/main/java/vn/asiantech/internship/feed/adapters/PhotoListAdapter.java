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
    private OnItemClickListener mListener;

    public PhotoListAdapter(Context context, int[] photos, OnItemClickListener listener) {
        this.mPhotoList = photos;
        this.mContext = context;
        this.mListener = listener;
    }

    public void setPhotoList(int[] photos) {
        this.mPhotoList = photos;
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
        ImageView imgPicture = (ImageView) view.findViewById(R.id.imgPhoto);
        ImageView imgPrevious = (ImageView) view.findViewById(R.id.imgPrevious);
        ImageView imgNext = (ImageView) view.findViewById(R.id.imgNext);

        imgPicture.setImageResource(mPhotoList[position]);
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowImageActivity.class);
                intent.putExtra(KEY_IMAGE, mPhotoList[position]);
                mContext.startActivity(intent);
            }
        });
        if (position > 0) {
            imgPrevious.setVisibility(View.VISIBLE);
            imgPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPreviousClick(position);
                }
            });
        } else {
            imgPrevious.setVisibility(View.GONE);
        }
        if (position < mPhotoList.length - 1) {
            imgNext.setVisibility(View.VISIBLE);
            imgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onNextClick(position);
                }
            });
        } else {
            imgNext.setVisibility(View.GONE);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ViewGroup) object);
    }

    /**
     * This interface used to handle on item of ViewPager click
     */
    public interface OnItemClickListener {
        void onPreviousClick(int position);

        void onNextClick(int position);
    }
}
