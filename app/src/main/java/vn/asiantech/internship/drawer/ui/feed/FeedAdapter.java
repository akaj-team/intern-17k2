package vn.asiantech.internship.drawer.ui.feed;

import android.content.Context;
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
import vn.asiantech.internship.drawer.models.FeedItem;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_STATUS = 0;
    private static final int TYPE_IMAGE = 1;

    private List<FeedItem> mFeedItems;

    public FeedAdapter(Context context, List<FeedItem> feedItems) {
        mFeedItems = feedItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_STATUS:
                return new ItemStatusViewHolder(inflater.inflate(R.layout.item_list_status, parent, false));
            case TYPE_IMAGE:
                return new ItemImageViewHolder(inflater.inflate(R.layout.item_list_image, parent, false));
            default:
                return new ItemCommentViewHolder(inflater.inflate(R.layout.item_list_comment, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemImageViewHolder) {
            ItemImageViewHolder imageViewHolder = (ItemImageViewHolder) holder;
        } else if (holder instanceof ItemStatusViewHolder) {
            ItemStatusViewHolder statusViewHolder = (ItemStatusViewHolder) holder;
//            statusViewHolder.mImgAvatar.setImageResource(R.drawable.ic_troll);
//            statusViewHolder.mTvName.setText(mFeedItems.get(position).getName());
        } else {
            ItemCommentViewHolder commentViewHolder = (ItemCommentViewHolder) holder;
            commentViewHolder.mTvComment.setText(mFeedItems.get(position).getComment());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_STATUS;
        }
        return TYPE_IMAGE;
    }

    @Override
    public int getItemCount() {
        return mFeedItems.size() + 1;
    }

    /**
     * viewholder of viewpager image
     */
    private class ItemImageViewHolder extends RecyclerView.ViewHolder {

        private ViewPager mViewPager;

        private List<Integer> mImages;

        ItemImageViewHolder(View itemView) {
            super(itemView);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            initImage();
            mViewPager.setAdapter(new FeedPagerAdapter(itemView.getContext(), mImages));
        }

        private void initImage() {
            mImages = new ArrayList<>();
            mImages.add(R.drawable.img_danang);
            mImages.add(R.drawable.img_danang);
            mImages.add(R.drawable.img_danang);
            mImages.add(R.drawable.img_danang);
            mImages.add(R.drawable.img_danang);
            mImages.add(R.drawable.img_danang);
        }
    }

    /**
     * viewholder of status
     */
    private class ItemStatusViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private ImageView mImgAvatar;

        ItemStatusViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }

    /**
     * viewholder of comment
     */
    private class ItemCommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvComment;

        ItemCommentViewHolder(View itemView) {
            super(itemView);
            mTvComment = (TextView) itemView.findViewById(R.id.tvComment);
        }
    }
}
