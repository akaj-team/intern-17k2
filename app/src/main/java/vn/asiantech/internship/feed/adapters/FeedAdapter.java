package vn.asiantech.internship.feed.adapters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.FeedItem;

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
    public void onBindViewHolder(final ItemFeedHolder holder, int position) {
        holder.mTvName.setText(mFeeds.get(position).getUserName());
        if (holder.mAdapter == null) {
            holder.mAdapter = new PhotoListAdapter(holder.mContext, mFeeds.get(position).getPhotoList());
            holder.mViewPagerPhotos.setOffscreenPageLimit(3);
            holder.mViewPagerPhotos.setAdapter(holder.mAdapter);
        } else {
            holder.mAdapter.setPhotoList(mFeeds.get(position).getPhotoList());
            holder.mAdapter.notifyDataSetChanged();
        }
        if (holder.mViewPagerPhotos.getCurrentItem() > 0) {
            holder.mImgPrevious.setVisibility(View.VISIBLE);
        }
        if (holder.mViewPagerPhotos.getCurrentItem() < holder.mAdapter.getCount() - 1) {
            holder.mImgNext.setVisibility(View.VISIBLE);
        }
        holder.mTvText.setText(mFeeds.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    /**
     * Item for FeedFragment
     */
    public final class ItemFeedHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ViewPager mViewPagerPhotos;
        private TextView mTvText;
        private ImageView mImgNext;
        private ImageView mImgPrevious;
        private PhotoListAdapter mAdapter;
        private Context mContext;

        private ItemFeedHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerPhotos = (ViewPager) itemView.findViewById(R.id.viewPagerPhotos);
            mTvText = (TextView) itemView.findViewById(R.id.tvText);
            mImgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            mImgPrevious = (ImageView) itemView.findViewById(R.id.imgPrevious);
            mViewPagerPhotos.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position < mAdapter.getCount() - 1) {
                        mImgNext.setVisibility(View.VISIBLE);
                    } else {
                        mImgNext.setVisibility(View.GONE);
                    }
                    if (position > 0) {
                        mImgPrevious.setVisibility(View.VISIBLE);
                    } else {
                        mImgPrevious.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            mImgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerPhotos.setCurrentItem(mViewPagerPhotos.getCurrentItem() + 1);
                }
            });

            mImgPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerPhotos.setCurrentItem(mViewPagerPhotos.getCurrentItem() - 1);
                }
            });
        }
    }
}
