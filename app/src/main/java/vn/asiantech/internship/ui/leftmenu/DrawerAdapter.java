package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * class adapter show list item on recyclerview
 * <p>
 * Created by Hai on 6/12/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 100;
    private static final int TYPE_ITEM = 101;

    private List<DrawerItem> mDrawerItems;
    private OnItemClickListener mOnItemClickListener;
    private Bitmap mBitmap;
    private Drawable mWallPaperDrawable;

    public DrawerAdapter(Context context, List<DrawerItem> items, OnItemClickListener onItemClickListener) {
        mDrawerItems = items;
        mOnItemClickListener = onItemClickListener;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        mWallPaperDrawable = wallpaperManager.getDrawable();
    }

    public void setAvatar(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
                return new ItemViewHolder(view);
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_header, parent, false);
                return new ItemHeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.mTvItem.setText(mDrawerItems.get(position - 1).getTitle());
            if (mDrawerItems.get(position - 1).isSelected()) {
                itemHolder.mTvItem.setTextColor(ContextCompat.getColor(itemHolder.itemView.getContext(), R.color.textview_text_select_color));
            } else {
                itemHolder.mTvItem.setTextColor(ContextCompat.getColor(itemHolder.itemView.getContext(), R.color.textview_text_color));
            }
            return;
        }
        if (holder instanceof ItemHeaderViewHolder) {
            final int sdk = Build.VERSION.SDK_INT;
            ItemHeaderViewHolder itemHeaderViewHolder = (ItemHeaderViewHolder) holder;
            if (sdk < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                itemHeaderViewHolder.mLlHeader.setBackgroundDrawable(mWallPaperDrawable);
            } else {
                itemHeaderViewHolder.mLlHeader.setBackground(mWallPaperDrawable);
            }
            if (mBitmap != null) {
                itemHeaderViewHolder.mImgAvatar.setImageBitmap(mBitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    /**
     * class set view to item
     */
    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvItem = (TextView) itemView.findViewById(R.id.tvItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition() - 1);
            }
        }
    }

    /**
     * class set view to header
     */
    private class ItemHeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlHeader;
        private CircleImageView mImgAvatar;

        ItemHeaderViewHolder(View itemView) {
            super(itemView);
            mLlHeader = (LinearLayout) itemView.findViewById(R.id.llHeader);
            mImgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
            mImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onAvatarClick();
                    }
                }
            });
        }
    }

    /**
     * interface using in MainActivity handle event item click, avatar click
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAvatarClick();
    }
}
