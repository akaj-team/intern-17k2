package vn.asiantech.internship.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to collect and display data on recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
class FeedAdapter extends RecyclerView.Adapter {

    private List<Feed> mFeeds = new ArrayList<>();
    private final Context mContext;
    private int mPosition;

    FeedAdapter(Context context, List<Feed> feeds) {
        this.mContext = context;
        this.mFeeds = feeds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_feed_list, parent, false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FeedViewHolder feedViewHolder = (FeedViewHolder) holder;
        feedViewHolder.mTvName.setText(mFeeds.get(position).getName());
        feedViewHolder.mTvDescription.setText(mFeeds.get(position).getDescription());
        feedViewHolder.mFeedViewPager.setAdapter(new FeedPagerAdapter(mContext, mFeeds.get(position).getImages()));
        mPosition = position;
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    /**
     * Used to register for feed.
     */
    private class FeedViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;
        private final TextView mTvDescription;
        private final ViewPager mFeedViewPager;
        private final ImageView mImgNext;
        private final ImageView mImgBack;

        FeedViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescribe);
            mFeedViewPager = (ViewPager) itemView.findViewById(R.id.viewPagerImage);
            mImgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            mImgBack = (ImageView) itemView.findViewById(R.id.imgBack);
            if (mFeeds.get(mPosition).getImages().size() == 1) {
                mImgBack.setVisibility(View.GONE);
                mImgNext.setVisibility(View.GONE);
            }
            mFeedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        mImgBack.setVisibility(View.GONE);
                    } else {
                        mImgBack.setVisibility(View.VISIBLE);
                    }
                    if (position == mFeeds.get(getAdapterPosition()).getImages().size() - 1) {
                        mImgNext.setVisibility(View.GONE);
                    } else {
                        mImgNext.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFeedViewPager.setCurrentItem(mFeedViewPager.getCurrentItem() + 1, true);
                    if (mFeedViewPager.getCurrentItem() == mFeeds.get(getAdapterPosition()).getImages().size() - 1) {
                        mImgNext.setVisibility(View.INVISIBLE);
                    }
                }
            });

            mImgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFeedViewPager.setCurrentItem(mFeedViewPager.getCurrentItem() - 1, true);
                    if (mFeedViewPager.getCurrentItem() == 0) {
                        mImgBack.setVisibility(View.INVISIBLE);
                    } else {
                        mImgBack.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    /**
     * Used to register for viewpager.
     */
    private class FeedPagerAdapter extends PagerAdapter {
        private final List<Image> mImages;
        private final LayoutInflater mInflater;
        private final Context mContext;

        FeedPagerAdapter(Context context, List<Image> images) {
            this.mImages = images;
            this.mContext = context;
            mInflater = LayoutInflater.from(mContext);
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
            View imageLayout = mInflater.inflate(R.layout.item_images, container, false);
            final ImageView imgItem = (ImageView) imageLayout.findViewById(R.id.imgItem);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
            imageLoader.displayImage(mImages.get(position).getLink(), imgItem);
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }
}
