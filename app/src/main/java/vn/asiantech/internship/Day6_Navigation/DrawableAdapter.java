package vn.asiantech.internship.Day6_Navigation;

import android.app.WallpaperManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by at-dinhvo on 12/06/2017.
 */

public class DrawableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 1;
    private List<DrawableItem> mTitles;
    private int mItemSelected = 0;
    private OnItemClickListener mOnItemClickListener;

    public DrawableAdapter(List<DrawableItem> titles, OnItemClickListener onItemClickListener) {
        mTitles = titles;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.header_drawable, parent, false));
            default:
                return new TitleViewHolder(inflater.inflate(R.layout.item_list_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.mTvTitle.setText(mTitles.get(position).getTitle());
            if (mTitles.get(position).isSelected()) {
                titleViewHolder.mTvTitle.setTextColor(Color.BLUE);
                titleViewHolder.mLinearLayout.setSelected(mTitles.get(position).isSelected());
            }
        } else {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(R.string.header_name);
            headerViewHolder.mTvEmail.setText(R.string.header_email);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private LinearLayout mLinearLayout;

        TitleViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvItemTitle);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lnItem);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvEmail;
        private ImageView mImgAvatar;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(itemView.getContext());
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            mImgAvatar.setImageDrawable(wallpaperDrawable);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
