package vn.asiantech.internship.drawer.ui.feed;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.models.FeedItem;

/**
 * Created by BACKDOOR on 07-Feb-17.
 */
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedItem> mFeedItems;

    FeedAdapter(List<FeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(mFeedItems.get(position).getName());
        holder.mTvComment.setText(mFeedItems.get(position).getComment());
        holder.mViewPager.setAdapter(new FeedPagerAdapter(mFeedItems.get(position).getImages()));
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvComment;
        private ViewPager mViewPager;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvFeed);
            mTvComment = (TextView) itemView.findViewById(R.id.tvComment);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mViewPager.setPageMargin(5); // TODO Convert 'px' to 'dp'
            mViewPager.setPageMarginDrawable(R.color.colorBlack);
        }
    }
}
