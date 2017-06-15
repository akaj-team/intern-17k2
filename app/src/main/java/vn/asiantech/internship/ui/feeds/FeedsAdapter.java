package vn.asiantech.internship.ui.feeds;

import android.content.Context;
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
 * Created by root on 6/15/17.
 */

public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private List<Feed> mFeeds;
    private Context mContext;

    public FeedsAdapter(List<Feed> Feeds, Context context) {
        this.mFeeds = Feeds;
        this.mContext = context;
    }

    @Override
    public FeedsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedsAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.mTvName.setText(mFeeds.get(position).getName());
        myViewHolder.mImgThumb.setImageResource(mFeeds.get(position).getIdImgThumb());
        myViewHolder.mImgAvatar.setImageResource(mFeeds.get(position).getIdImgAvatar());
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
        private ImageView mImgThumb;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mImgThumb = (ImageView) itemView.findViewById(R.id.imgThumb);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
