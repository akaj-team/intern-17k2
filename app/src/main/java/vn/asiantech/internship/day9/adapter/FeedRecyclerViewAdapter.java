package vn.asiantech.internship.day9.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day9.model.User;

/**
 * Created by rimoka on 15/06/2017.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ItemViewHolder> {
    private List<User> mUsers;
    private Context mContext;
    private CustomFeedPagerAdapter mPagerAdapter;
    private int[] mImages = {
            R.mipmap.img_pug,
            R.mipmap.img_pugdt,
            R.mipmap.img_pugxd,
            R.mipmap.img_pugxx
    };

    public FeedRecyclerViewAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
        mPagerAdapter = new CustomFeedPagerAdapter(mImages, mContext);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mImgUser.setImageDrawable(ContextCompat.getDrawable(mContext, mUsers.get(position).getImage()));
        holder.mTvUser.setText(mUsers.get(position).getName());
        holder.mTvDescription.setText(mUsers.get(position).getDescription());
        holder.mViewPagerFeed.setAdapter(mPagerAdapter);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    /**
     * create ItemviewHolder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvUser;
        private TextView mTvDescription;
        private ImageView mImgUser;
        private ViewPager mViewPagerFeed;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvUser = (TextView) itemView.findViewById(R.id.tvUser);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            mViewPagerFeed = (ViewPager) itemView.findViewById(R.id.viewPagerFeed);

        }
    }
}
