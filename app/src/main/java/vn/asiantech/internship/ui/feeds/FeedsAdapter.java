package vn.asiantech.internship.ui.feeds;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Feed;

/**
 * Created by root on 6/15/17.
 * Feed Adapter
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.MyViewHolder> {

    private List<Feed> mFeeds;

    FeedsAdapter(List<Feed> feeds) {
        this.mFeeds = feeds;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        myViewHolder.mTvName.setText(mFeeds.get(position).getName());
        Picasso.with(myViewHolder.itemView.getContext()).load(mFeeds.get(position).getIdImgAvatar()).into(myViewHolder.mImgAvatar);
        myViewHolder.mTvDescription.setText(mFeeds.get(position).getDescription());
        myViewHolder.mViewPager.setAdapter(new ImageAdapter(myViewHolder.itemView.getContext(), mFeeds.get(position).getIdImgThumb()));
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    /**
     * Viewhoder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private ImageView mImgAvatar;
        private ViewPager mViewPager;
        private TextView mTvDescription;

        MyViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mViewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }

    /**
     * Used to register for viewpager.
     */
    private static class ImageAdapter extends PagerAdapter {
        private String[] mImages;
        private LayoutInflater mInflater;
        private Context mContext;

        ImageAdapter(Context context, String[] images) {
            mImages = images;
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageLayout = mInflater.inflate(R.layout.item_image, container, false);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imgThumb);
            Picasso.with(mContext).load(mImages[position]).into(imageView);
            container.addView(imageLayout, 0);
            return imageLayout;
        }
    }
}
