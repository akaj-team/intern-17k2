package vn.asiantech.internship.feed;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyHolder> {

    private List<Feed> mFeedList = new ArrayList<>();

    FeedAdapter(List<Feed> feedList) {

        mFeedList = feedList;
    }

    /**
     * Copyright © 2016 AsianTech inc.
     * Created by datbu on 15-06-2017.
     */
    class MyHolder extends RecyclerView.ViewHolder {
        private ViewPager mViewPager;
        private TextView mTvTitle;
        private TextView mTvName;
        private Context mContext;
        private ViewPagerAdapter mViewPagerAdapter;

        MyHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvName = (TextView) itemView.findViewById(R.id.tvStatus);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvImgTitle);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_feed, parent, false);
        return new MyHolder(parent.getContext(), v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Feed feed = mFeedList.get(position);
        holder.mTvTitle.setText(feed.getTitle());
        holder.mTvName.setText(feed.getName());
        if (holder.mViewPagerAdapter == null) {
            holder.mViewPagerAdapter = new ViewPagerAdapter(holder.mContext, feed.getImages());
            holder.mViewPager.setAdapter(holder.mViewPagerAdapter);
        } else {
            holder.mViewPagerAdapter.setImage(feed.getImages());
            holder.mViewPagerAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return mFeedList.size();

    }
}
