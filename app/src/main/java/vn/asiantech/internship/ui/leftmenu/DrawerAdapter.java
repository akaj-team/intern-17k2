package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * Used to collect and display data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class DrawerAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private final OnItemClickListener mListener;
    private final Drawable mDrawable;

    public DrawerAdapter(Context context, List<DrawerItem> drawerItems, OnItemClickListener listener) {
        mDrawerItems = drawerItems;
        mListener = listener;
        WallpaperManager wallPagerManager = WallpaperManager.getInstance(context);
        mDrawable = wallPagerManager.getDrawable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new ItemHeaderViewHolder(inflater.inflate(R.layout.item_drawer_header, parent, false));
            default:
                return new ItemViewHolder(inflater.inflate(R.layout.item_drawer, parent, false));
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHeaderViewHolder) {
            ItemHeaderViewHolder itemHeaderViewHolder = (ItemHeaderViewHolder) holder;
            itemHeaderViewHolder.mImgBackground.setImageDrawable(mDrawable);
            return;
        }
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvName.setText(mDrawerItems.get(position - 1).getName());
            if (mDrawerItems.get(position - 1).isChecked()) {
                itemViewHolder.mTvName.setBackgroundResource(R.drawable.bg_press_item_list_drawer);
            } else {
                itemViewHolder.mTvName.setBackgroundResource(R.drawable.bg_default_item_list_drawer);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size() + 1;
    }

    /**
     * Used to register for DrawerItem in item of drawerLayout
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;

        private ItemViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick(getLayoutPosition() - 1);
                }
            });
        }
    }

    /**
     * Used to register for DrawerItem in header of drawerLayout
     */
    private class ItemHeaderViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImgAvatar;
        private final ImageView mImgBackground;

        private ItemHeaderViewHolder(final View itemView) {
            super(itemView);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mImgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            mImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAvatarClick();
                    }
                }
            });
        }
    }

    /**
     * Use to get click listener for items in recyclerView
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAvatarClick();
    }
}
