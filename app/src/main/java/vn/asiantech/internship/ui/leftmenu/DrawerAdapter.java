package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;

/**
 * Created by Thanh Thien on 6/12/17
 * Using for Drawer
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 0;

    private String[] mItems;
    private int mPositionSelected = -1; // mPositionSelected = -1 if nothing select
    private Bitmap mBitmap;
    private Drawable mWallpaperDrawable;
    private OnItemsListener mOnItemsListener;

    DrawerAdapter(Context context, String[] items, OnItemsListener onItemsListener) {
        mItems = items;
        mOnItemsListener = onItemsListener;

        //Get current wallpaper
        WallpaperManager mWallpaperManager = WallpaperManager.getInstance(context);
        mWallpaperDrawable = mWallpaperManager.getDrawable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                return new ItemHeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawer_header, viewGroup, false));
            default:
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawer, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder myItemViewHolder = (ItemViewHolder) viewHolder;
            myItemViewHolder.mTvName.setText(mItems[position - 1]);
            if (position == mPositionSelected) {
                myItemViewHolder.itemView.setBackgroundColor(myItemViewHolder.itemView.getContext().getResources().getColor(R.color.colorItemChoise));
            } else {
                myItemViewHolder.itemView.setBackgroundColor(myItemViewHolder.itemView.getContext().getResources().getColor(R.color.colorItem));
            }
        } else if (viewHolder instanceof ItemHeaderViewHolder) {
            ItemHeaderViewHolder myHeaderViewHolder = (ItemHeaderViewHolder) viewHolder;
            myHeaderViewHolder.mClParent.setBackgroundDrawable(mWallpaperDrawable);
            myHeaderViewHolder.mTvAuthorName.setText(myHeaderViewHolder.itemView.getContext().getString(R.string.app_author));
            if (mBitmap != null) {
                myHeaderViewHolder.mCivAvatar.setImageBitmap(mBitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.length + 1;
    }

    /**
     * ItemView for content list
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;

        ItemViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemsListener != null) {
                        mOnItemsListener.onItemCLick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * View for header
     */
    public class ItemHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvAuthorName;
        private CircleImageView mCivAvatar;
        private ConstraintLayout mClParent;

        ItemHeaderViewHolder(final View itemView) {
            super(itemView);
            mTvAuthorName = (TextView) itemView.findViewById(R.id.tvAuthorName);
            mCivAvatar = (CircleImageView) itemView.findViewById(R.id.civAvatar);
            mClParent = (ConstraintLayout) itemView.findViewById(R.id.clParent);
            mCivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemsListener != null) {
                        mOnItemsListener.showDialogChoice();
                    }
                }
            });
        }
    }

    /**
     * @param positionSelected is a position of item selected
     */
    void setPositionSelected(int positionSelected) {
        mPositionSelected = positionSelected;
        notifyDataSetChanged();
    }

    /**
     * @param bitmap is link of image
     */
    void setImageAvatar(Bitmap bitmap) {
        mBitmap = bitmap;
        notifyDataSetChanged();
    }

    /**
     * Interface for fragment
     */
    public interface OnItemsListener {
        void showDialogChoice();

        void onItemCLick(int position);
    }
}
