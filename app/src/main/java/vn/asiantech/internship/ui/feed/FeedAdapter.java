package vn.asiantech.internship.ui.feed;

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
import vn.asiantech.internship.models.ExpandableTextView;
import vn.asiantech.internship.models.FeedItem;

/**
 * adapter of recyclerview Feed
 * <p>
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
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.mTvName.setText(mFeedItems.get(position).getName());
        holder.mTvStatus.setText(mFeedItems.get(position).getStatus());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mFeedItems.get(position).getImageArray());
        holder.mViewPagerImage.setAdapter(viewPagerAdapter);
        holder.updateArrow(position);
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size();
    }

    /**
     * init view and set data to view
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private ViewPager mViewPagerImage;
        private ImageView mImgArrowLeft;
        private ImageView mImgArrowRight;
        private ExpandableTextView mTvStatus;

        ItemViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerImage = (ViewPager) itemView.findViewById(R.id.viewPagerImage);
            mImgArrowLeft = (ImageView) itemView.findViewById(R.id.imgArrowLeft);
            mImgArrowRight = (ImageView) itemView.findViewById(R.id.imgArrowRight);
            mTvStatus = (ExpandableTextView) itemView.findViewById(R.id.tvStatus);
            mViewPagerImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    updateArrow(getLayoutPosition());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mImgArrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerImage.setCurrentItem(mViewPagerImage.getCurrentItem() - 1);
                }
            });
            mImgArrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerImage.setCurrentItem(mViewPagerImage.getCurrentItem() + 1);
                }
            });
        }

        private void updateArrow(int position) {
            String[] mImageArray = mFeedItems.get(position).getImageArray();
            if (mImageArray.length > 1) {
                mImgArrowLeft.setVisibility(mViewPagerImage.getCurrentItem() != 0 ? View.VISIBLE : View.GONE);
                mImgArrowRight.setVisibility(mViewPagerImage.getCurrentItem() != mImageArray.length - 1 ? View.VISIBLE : View.GONE);
            } else {
                mImgArrowLeft.setVisibility(View.GONE);
                mImgArrowRight.setVisibility(View.GONE);
            }
        }
    }
}
