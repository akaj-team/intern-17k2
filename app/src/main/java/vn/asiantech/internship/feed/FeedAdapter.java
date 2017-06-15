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
import vn.asiantech.internship.models.FeedItem;

/**
 * Created by Hai on 6/15/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemViewHolder> {
    private Context mContext;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<FeedItem> mFeedItems = new ArrayList<>();

    public FeedAdapter(Context context, List<FeedItem> feedItems) {
        mContext = context;
        mFeedItems = feedItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_feed, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mTvName.setText(mFeedItems.get(position).getName());
        holder.mTvStatus.setText(mFeedItems.get(position).getStatus());
        mViewPagerAdapter = new ViewPagerAdapter(mContext, mFeedItems.get(position).getImageArray());
        holder.mViewPagerImage.setAdapter(mViewPagerAdapter);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ViewPager mViewPagerImage;
        private TextView mTvStatus;
        private ViewPagerAdapter mViewPagerAdapter;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerImage = (ViewPager) itemView.findViewById(R.id.viewPager);
            mTvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        }
    }
}
