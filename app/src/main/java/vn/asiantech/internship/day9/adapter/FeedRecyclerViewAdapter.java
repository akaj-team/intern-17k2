package vn.asiantech.internship.day9.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
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
import vn.asiantech.internship.day9.database.ImageModify;
import vn.asiantech.internship.day9.model.Image;
import vn.asiantech.internship.day9.model.User;

/**
 * Created by at-hoavo on 15/06/2017.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.ItemViewHolder> {
    private List<User> mUsers;
    private Context mContext;
    private List<Image> mImages = new ArrayList<>();

    public FeedRecyclerViewAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
        initListImage();
        seperateLink();
    }

    private void initListImage() {
        ImageModify imageModify = new ImageModify(mContext);
        Cursor cursor = imageModify.getInformation();
        while (!cursor.isAfterLast()) {
            Image image = new Image(cursor.getInt(cursor.getColumnIndex(imageModify.KEY_ID)), cursor.getString(cursor.getColumnIndex(imageModify.KEY_TITLE)), cursor.getString(cursor.getColumnIndex(imageModify.KEY_DESCRIPTION)), cursor.getString(cursor.getColumnIndex(imageModify.KEY_LINK)));
            mImages.add(image);
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        CustomFeedPagerAdapter pagerAdapter = null;
        holder.mImgUser.setImageDrawable(ContextCompat.getDrawable(mContext, mUsers.get(position).getImage()));
        holder.mTvUser.setText(mUsers.get(position).getName());
        holder.mTvDescription.setText(mUsers.get(position).getDescription());
        pagerAdapter = new CustomFeedPagerAdapter(mImages.get(position).getLinks(), mContext);
        if (mImages.get(position).getLinks().size() > 1) {
            holder.mImgArrowRight.setVisibility(View.VISIBLE);
        } else {
            holder.mImgArrowRight.setVisibility(View.INVISIBLE);
            holder.mImgArrowLeft.setVisibility(View.INVISIBLE);
        }
        holder.mViewPagerFeed.setAdapter(pagerAdapter);
        holder.mViewPagerFeed.getAdapter().notifyDataSetChanged();
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
        private ImageView mImgArrowLeft;
        private ImageView mImgArrowRight;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvUser = (TextView) itemView.findViewById(R.id.tvUser);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            mImgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            mViewPagerFeed = (ViewPager) itemView.findViewById(R.id.viewPagerFeed);
            mImgArrowLeft = (ImageView) itemView.findViewById(R.id.imgArrowLeft);
            mImgArrowRight = (ImageView) itemView.findViewById(R.id.imgArrowRight);
            mViewPagerFeed.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position != 0) {
                        mImgArrowLeft.setVisibility(View.VISIBLE);
                    } else {
                        mImgArrowLeft.setVisibility(View.INVISIBLE);
                    }
                    if (position != (mImages.get(getAdapterPosition()).getLinks().size()) - 1) {
                        mImgArrowRight.setVisibility(View.VISIBLE);
                    } else {
                        mImgArrowRight.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            mImgArrowLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPagerFeed.setCurrentItem(mViewPagerFeed.getCurrentItem() - 1);
                }
            });
            mImgArrowRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() < mImages.size()) {
                        mViewPagerFeed.setCurrentItem(mViewPagerFeed.getCurrentItem() + 1);
                    }
                }
            });
        }
    }

    private void seperateLink() {
        for (int j = 0; j < mImages.size(); j++) {
            List<String> links = mImages.get(j).getLinks();
            String link = mImages.get(j).getLink().trim();
            int vitri = 0;
            if (link.contains(",")) {
                for (int i = 0; i < link.length(); i++) {
                    if (link.charAt(i) == ',') {
                        links.add(link.substring(vitri, i));
                        vitri = i + 1;
                    }
                }
                links.add(link.substring(vitri + 1, link.length()));
            } else {
                links.add(link);
            }
            mImages.get(j).setLinks(links);
        }
    }
}
