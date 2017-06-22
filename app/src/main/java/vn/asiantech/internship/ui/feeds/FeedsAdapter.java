package vn.asiantech.internship.ui.feeds;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Feed;

/**
 * Created by root on 6/15/17.
 * Feed Adapter
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private List<Feed> mFeeds;

    FeedsAdapter(List<Feed> feeds) {
        mFeeds = feeds;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.mTvName.setText(mFeeds.get(position).getName());
        myViewHolder.mImgAvatar.setImageResource(R.drawable.ic_one);
        myViewHolder.mTvDescription.setText(mFeeds.get(position).getDescription());
        myViewHolder.mViewPager.setAdapter(new ImageAdapter(myViewHolder.itemView.getContext(), mFeeds.get(position).getIdImgThumbs()));
        myViewHolder.setArrow(position, myViewHolder.mViewPager.getCurrentItem());
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    /**
     * Viewhoder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private ImageView mImgAvatar;
        private ViewPager mViewPager;
        private ImageView mImgBack;
        private ImageView mImgNext;
        private TextView mTvDescription;

        MyViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgBack = (ImageView) itemView.findViewById(R.id.imgBack);
            mImgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            mImgBack.setVisibility(View.GONE);
            mImgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setArrow(getItemCount() - 1, mViewPager.getCurrentItem() - 1);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            });

            mImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setArrow(getItemCount() - 1, mViewPager.getCurrentItem() + 1);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            });
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    setArrow(getItemCount() - 1, mViewPager.getCurrentItem());
                }
            });

        }

        void setArrow(int posItemParent, int posImage) {
            int sizeArray = mFeeds.get(posItemParent).getIdImgThumbs().length;
            if (sizeArray == 1 || posImage == sizeArray - 1) {
                mImgNext.setVisibility(View.GONE);
            } else {
                mImgNext.setVisibility(View.VISIBLE);
            }
            if (sizeArray == 1 || posImage == 0) {
                mImgBack.setVisibility(View.GONE);
            } else {
                mImgBack.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * Used to register for viewpager.
     */
    private static class ImageAdapter extends PagerAdapter {
        private String[] mImages;
        private LayoutInflater mInflater;
        private Context mContext;
        private ImageLoader mImageLoader;
        private DisplayImageOptions mDisplayImageOptions;

        ImageAdapter(Context context, String[] images) {
            mImages = images;
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
            mDisplayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_no_internet).showImageOnFail(R.drawable.ic_no_internet).build();
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageLayout = mInflater.inflate(R.layout.item_image, container, false);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgThumb);
            mImageLoader.displayImage(mImages[position], imageView, mDisplayImageOptions);
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }
}
