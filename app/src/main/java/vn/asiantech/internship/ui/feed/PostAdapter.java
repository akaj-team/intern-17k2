package vn.asiantech.internship.ui.feed;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> mPosts;

    public PostAdapter(List<Post> posts) {
        mPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        holder.mImgUser.setImageBitmap(mPosts.get(position).getImageUSer());
        holder.mTvName.setText(mPosts.get(position).getName());
        final int[] currentItem = {holder.mViewPagerImage.getCurrentItem()};
        ImageAdapter imageAdapter = new ImageAdapter(mPosts.get(position).getImageList(), new ImageAdapter.OnClickArrowListener() {
            @Override
            public void onClickLeft() {
                currentItem[0] -= 1;
                holder.mViewPagerImage.setCurrentItem(currentItem[0]);
            }

            @Override
            public void onClickRight() {
                currentItem[0] += 1;
                holder.mViewPagerImage.setCurrentItem(currentItem[0]);
            }
        });
        holder.mViewPagerImage.setAdapter(imageAdapter);
        holder.mTvDescription.setText(mPosts.get(position).getDesription());
    }

    @Override
    public int getItemCount() {
        return mPosts == null ? 0 : mPosts.size();
    }

    /**
     * holder view in RecyclerView Post
     */
    class PostViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImgUser;
        private TextView mTvName;
        private ViewPager mViewPagerImage;
        private TextView mTvDescription;

        public PostViewHolder(View itemView) {
            super(itemView);
            mImgUser = (CircleImageView) itemView.findViewById(R.id.imgUser);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mViewPagerImage = (ViewPager) itemView.findViewById(R.id.viewPagerImage);
            mTvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }
    }
}
