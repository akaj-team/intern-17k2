package vn.asiantech.internship.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Food;

/**
 * Adapter for Feed
 * Created by huypham on 15/06/2017.
 * Change on 19/06/2017 - add button next and previous
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Food> mFoodLists;

    public FeedAdapter(List<Food> foodLists) {
        mFoodLists = foodLists;
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
            item.mImageAdapter = new ImageAdapter(item.mContext, mFoodLists.get(position).getImage());
            item.mViewPager.setAdapter(item.mImageAdapter);

            item.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int lastPosition = item.mImageAdapter.getCount();
                    if (position == 0) {
                        item.mBtnPrevious.setVisibility(View.INVISIBLE);
                    } else if (position == (lastPosition - 1)) {
                        item.mBtnNext.setVisibility(View.INVISIBLE);
                    } else {
                        item.mBtnPrevious.setVisibility(View.VISIBLE);
                        item.mBtnNext.setVisibility(View.VISIBLE);
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
        return mFoodLists.size();
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
        private Button mBtnPrevious;
        private Button mBtnNext;

        private ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mBtnNext = (Button) itemView.findViewById(R.id.btnNext);
            mBtnPrevious = (Button) itemView.findViewById(R.id.btnPrevious);

            mBtnNext.setOnClickListener(this);
            mBtnPrevious.setOnClickListener(this);
            mBtnNext.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_keyboard_arrow_right, 0, 0, 0);
            mBtnPrevious.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_keyboard_arrow_left, 0, 0, 0);

        }

        private void setData(int position) {
            Food food = mFoodLists.get(position);
            mTvUserName.setText(food.getFoodName());
            mTvContent.setText(food.getContent());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNext:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    break;
                case R.id.btnPrevious:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    break;
            }
        }
    }
}
