package vn.asiantech.internship.feed;

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
 *
 * Created by Hai on 6/15/2017.
 */

class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemViewHolder> {
    private List<FeedItem> mFeedItems = new ArrayList<>();

    FeedAdapter(List<FeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mTvName.setText(mFeedItems.get(position).getName());
        holder.mTvStatus.setText(mFeedItems.get(position).getStatus());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(holder.itemView.getContext(), mFeedItems.get(position).getImageArray());
        holder.mViewPagerImage.setAdapter(viewPagerAdapter);
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ViewPager mViewPagerImage;
        private TextView mTvStatus;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerImage = (ViewPager) itemView.findViewById(R.id.viewPagerImage);
            mTvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
        }
    }
}
