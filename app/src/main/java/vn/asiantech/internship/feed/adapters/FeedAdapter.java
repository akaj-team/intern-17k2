package vn.asiantech.internship.feed.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemFeedHolder> {
    private List<FeedItem> mFeeds;

    public FeedAdapter(List<FeedItem> feeds) {
        mFeeds = feeds;
    }

    @Override
    public ItemFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new ItemFeedHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(ItemFeedHolder holder, int position) {
        holder.mTvName.setText(mFeeds.get(position).getUserName());
        if (holder.mAdapter == null) {
            holder.mAdapter = new PhotoListAdapter(holder.mContext, mFeeds.get(position).getPhotoList());
            holder.mViewPagerPhotos.setAdapter(holder.mAdapter);
        } else {
            holder.mAdapter.setmPhotoList(mFeeds.get(position).getPhotoList());
            holder.mAdapter.notifyDataSetChanged();
        }
        holder.mTvText.setText(mFeeds.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    public class ItemFeedHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ViewPager mViewPagerPhotos;
        private TextView mTvText;
        private PhotoListAdapter mAdapter;
        private Context mContext;

        private ItemFeedHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerPhotos = (ViewPager) itemView.findViewById(R.id.viewPagerPhotos);
            mTvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
}
