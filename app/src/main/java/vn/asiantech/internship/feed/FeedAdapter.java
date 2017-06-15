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
 * Created by sony on 15/06/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter {

    private List<Feed> feeds = new ArrayList<>();
    private Context mContext;

    public FeedAdapter(Context context, List<Feed> feeds) {
        this.mContext = context;
        this.feeds = feeds;
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
        myViewHolder.mTvName.setText(feeds.get(position).getName());
        myViewHolder.mTvDescription.setText(feeds.get(position).getDescription());
        myViewHolder.mViewPager.setAdapter(new FeedPagerAdapter(mContext, feeds.get(position).getImages()));
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvDescription;
        private ViewPager mViewPager;

        public FeedViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescribe);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        }
    }

    public static class FeedPagerAdapter extends PagerAdapter {
        private List<Integer> images;
        private LayoutInflater inflater;
        private Context mContext;

        public FeedPagerAdapter(Context context, List<Integer> images) {
            this.images = images;
            this.mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return images.size();
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
            View imageLayout = inflater.inflate(R.layout.item_images, container, false);
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgItem);
            imageView.setImageResource(images.get(position));
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }
}
