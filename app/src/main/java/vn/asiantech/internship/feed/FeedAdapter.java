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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FeedViewHolder myViewHolder = (FeedViewHolder) holder;
        myViewHolder.mTvName.setText(mFeeds.get(position).getName());
        myViewHolder.mTvDescription.setText(mFeeds.get(position).getDescription());
        myViewHolder.mViewPager.setAdapter(new FeedPagerAdapter(mContext, mFeeds.get(position).getImages()));
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
        private final ViewPager mViewPager;

        FeedViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescribe);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPagerImage);
        }
    }

    /**
     * Used to register for viewpager.
     */
    private static class FeedPagerAdapter extends PagerAdapter {
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
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgItem);
          //  imageView.setImageResource(mImages.get(position).getLink());
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }
}
