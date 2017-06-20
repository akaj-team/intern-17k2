package vn.asiantech.internship.feed;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewPager.OnPageChangeListener {
        private ViewPager mViewPager;
        private TextView mTvTitle;
        private TextView mTvName;
        private Context mContext;
        private ImageView mImgNext;
        private ImageView mImgPrev;
        private ViewPagerAdapter mViewPagerAdapter;

        MyHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvName = (TextView) itemView.findViewById(R.id.tvStatus);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvImgTitle);
            mImgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            mImgPrev = (ImageView) itemView.findViewById(R.id.imgPrev);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position < mViewPagerAdapter.getCount() - 1) {
                        mImgNext.setVisibility(View.VISIBLE);
                    } else {
                        mImgNext.setVisibility(View.INVISIBLE);
                    }
                    if (position > 0) {
                        mImgPrev.setVisibility(View.VISIBLE);
                    } else {
                        mImgPrev.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            mImgNext.setOnClickListener(this);
            mImgPrev.setOnClickListener(this);
            Log.d("tag", "MyHolder: " + itemView.getId());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgNext:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    break;
                case R.id.imgPrev:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
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
            holder.mViewPagerAdapter = new ViewPagerAdapter(holder.mContext, feed.getStrimages());
            holder.mViewPager.setAdapter(holder.mViewPagerAdapter);
        } else {
            holder.mViewPagerAdapter.setImage(feed.getStrimages());
            holder.mViewPagerAdapter.notifyDataSetChanged();
        }
        if (holder.mViewPager.getCurrentItem() > 0) {
            holder.mImgPrev.setVisibility(View.VISIBLE);
        }
        if (holder.mViewPager.getCurrentItem() < holder.mViewPagerAdapter.getCount() - 1) {
            holder.mImgNext.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return mFeedList.size();
    }
}
