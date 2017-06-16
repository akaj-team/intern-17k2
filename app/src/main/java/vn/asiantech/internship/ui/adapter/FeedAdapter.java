package vn.asiantech.internship.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Food;

/**
 * Adapter for Feed
 * Created by huypham on 15/06/2017.
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
            ViewHolder item = (ViewHolder) holder;
            item.setData(position);
            item.setData(position);
            if (item.mImageAdapter == null) {
                item.mImageAdapter = new ImageAdapter(item.mContext, mFoodLists.get(position).getImage());
                item.mViewPager.setAdapter(item.mImageAdapter);
            } else {
                item.mImageAdapter.setImageLists(mFoodLists.get(position).getImage());
                item.mImageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFoodLists.size();
    }

    /**
     * View Holder for Feed Item
     * Created by huypham on 15/6/2017
     */

    private class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvUserName;
        private final TextView mTvContent;
        private final ViewPager mViewPager;
        private ImageAdapter mImageAdapter;
        private final Context mContext;

        private ViewHolder(Context context, View itemView) {
            super(itemView);
            mContext = context;
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvContent = (TextView) itemView.findViewById(R.id.tvContent);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
        }

        private void setData(int position) {
            Food food = mFoodLists.get(position);
            mTvUserName.setText(food.getFoodName());
            mTvContent.setText(food.getContent());
        }
    }
}
