package vn.asiantech.internship.ui.adapters;

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
import vn.asiantech.internship.models.Feed;

/**
 * Adapter for Feed
 * Created by huypham on 15/06/2017.
 * Change on 19/06/2017 - add button next and previous
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Feed> mFeedList;

    public FeedAdapter(List<Feed> feedList) {
        mFeedList = feedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(parent.getContext(), inflater.inflate(R.layout.item_list_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder item = (ViewHolder) holder;
            item.setData(position);
            item.mImageAdapter = new ImageAdapter(item.mContext, mFeedList.get(position).getImageList());
            item.mViewPager.setAdapter(item.mImageAdapter);

            item.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int lastPosition = item.mImageAdapter.getCount();
                    if (position > 0) {
                        item.mImgPrevious.setVisibility(View.VISIBLE);
                    } else {
                        item.mImgPrevious.setVisibility(View.INVISIBLE);
                    }
                    if (position < (lastPosition - 1)) {
                        item.mImgNext.setVisibility(View.VISIBLE);
                    } else {
                        item.mImgNext.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    /**
     * View Holder for Feed Item
     * Created by huypham on 15/6/2017
     * Change on 19/06/2017 - press on next and previous
     */
    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvUserName;
        private TextView mTvContent;
        private ViewPager mViewPager;
        private ImageAdapter mImageAdapter;
        private Context mContext;
        private ImageView mImgPrevious;
        private ImageView mImgNext;

        private ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mImgNext = (ImageView) itemView.findViewById(R.id.imgNext);
            mImgPrevious = (ImageView) itemView.findViewById(R.id.imgPrevious);

            mImgNext.setOnClickListener(this);
            mImgPrevious.setOnClickListener(this);
        }

        private void setData(int position) {
            Feed feed = mFeedList.get(position);
            mTvUserName.setText(feed.getTitle());
            mTvContent.setText(feed.getDescription());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgNext:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    break;
                case R.id.imgPrevious:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    break;
            }
        }
    }
}
