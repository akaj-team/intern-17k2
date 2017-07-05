package vn.asiantech.internship.drawer.ui.feed;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.models.FeedItem;

import static vn.asiantech.internship.R.id.viewPager;

/**
 * Created by BACKDOOR on 07-Feb-17.
 */
class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedItem> mFeedItems;
    private Context mContext;

    FeedAdapter(Context context, List<FeedItem> feedItems) {
        mContext = context;
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
        FeedPagerAdapter pagerAdapter = new FeedPagerAdapter(mContext, mFeedItems.get(position).getImages());
        holder.mViewPager.setAdapter(pagerAdapter);
        if (mFeedItems.get(position).getImages().length > 1) {
            holder.mBtnRightSlide.setVisibility(View.VISIBLE);
        }
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
        private ImageButton mBtnLeftSlide;
        private ImageButton mBtnRightSlide;

        ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvFeed);
            mTvComment = (TextView) itemView.findViewById(R.id.tvComment);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mBtnLeftSlide = (ImageButton) itemView.findViewById(R.id.imgBtnLeftSlide);
            mBtnRightSlide = (ImageButton) itemView.findViewById(R.id.imgBtnRightSlide);
            mViewPager.setPageMargin(5);
            mViewPager.setPageMarginDrawable(R.color.colorBlack);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            mBtnLeftSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                }
            });
            mBtnRightSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            });
        }

        private void updateArrow(int position) {
            String[] mImageArray = mFeedItems.get(position).getImages();
            if (mImageArray.length > 1) {
                mBtnLeftSlide.setVisibility(mViewPager.getCurrentItem() != 0 ? View.VISIBLE : View.GONE);
                mBtnRightSlide.setVisibility(mViewPager.getCurrentItem() != mImageArray.length - 1 ? View.VISIBLE : View.GONE);
            } else {
                mBtnLeftSlide.setVisibility(View.GONE);
                mBtnRightSlide.setVisibility(View.GONE);
            }
        }
    }
}
