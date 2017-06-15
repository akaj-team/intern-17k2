package vn.asiantech.internship.ui.feeds;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Feed;

/**
 * Created by root on 6/15/17.
 * Feed Adapter
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private List<Feed> mFeeds;
    private LinearLayout thumbnailsContainer;

    FeedsAdapter(List<Feed> Feeds) {
        this.mFeeds = Feeds;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.mTvName.setText(mFeeds.get(position).getName());
        myViewHolder.mImgAvatar.setImageResource(mFeeds.get(position).getIdImgAvatar());
        myViewHolder.mViewPager.setAdapter(myViewHolder.mFeedImagesAdapter);

    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    /**
     * Viewhoder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTvName;
        private ImageView mImgAvatar;
        private ViewPager mViewPager;
        private FeedImagesAdapter mFeedImagesAdapter;
        private List<Integer> mImages;

        MyViewHolder(View itemView) {
            super(itemView);
            mImages = new ArrayList<>();
            createDataImages();
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            if (itemView.getContext() instanceof FeedsActivity) {
                mFeedImagesAdapter = new FeedImagesAdapter(mImages, ((FeedsActivity) itemView.getParent()));
            }
        }

        @Override
        public void onClick(View v) {

        }

        void createDataImages() {
            for (int i = 0; i < 10; i++) {
                if (i % 2 == 0) {
                    mImages.add(R.drawable.bg_steve);
                } else {
                    mImages.add(R.drawable.bg_stevi);
                }
            }
        }
    }
}
