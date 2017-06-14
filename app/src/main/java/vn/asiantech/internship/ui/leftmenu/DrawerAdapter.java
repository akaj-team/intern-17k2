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

    private final int TYPE_ITEM = 1;
    private final int TYPE_HEADER = 0;

    private Context mContext;
    private String[] mItems;
    private int mPositionSelected = -1; // mPositionSelected = -1 if nothing select
    private Bitmap mBitmap;
    private Drawable mWallpaperDrawable;
    private OnItemsListener mOnItemsListener;

    DrawerAdapter(Context context, String[] items, OnItemsListener onItemsListener) {
        mItems = items;
        mContext = context;
        mOnItemsListener = onItemsListener;

        //Get current wallpaper
        WallpaperManager mWallpaperManager = WallpaperManager.getInstance(mContext);
        mWallpaperDrawable = mWallpaperManager.getDrawable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_drawer_header, viewGroup, false));
            case TYPE_ITEM:
                return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_drawer, viewGroup, false));
            default:
                return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_drawer, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder myViewHolder = (ItemViewHolder) viewHolder;
            myViewHolder.mTextView.setText(mItems[position]);
            if (position == mPositionSelected) {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItemChoise));
            } else {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItem));
            }
        } else if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder myViewHolder = (HeaderViewHolder) viewHolder;
            myViewHolder.mCLParent.setBackgroundDrawable(mWallpaperDrawable);
            myViewHolder.mTvAuthorName.setText(mContext.getString(R.string.app_author));

            if (mBitmap != null) {
                myViewHolder.mCIVAvatar.setImageBitmap(mBitmap);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.length - 1;
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
     * ItemView for content list
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    /**
     * View for header
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvAuthorName;
        private CircleImageView mCIVAvatar;
        private ConstraintLayout mCLParent;

        HeaderViewHolder(final View itemView) {
            super(itemView);
            mTvAuthorName = (TextView) itemView.findViewById(R.id.tvAuthorName);
            mCIVAvatar = (CircleImageView) itemView.findViewById(R.id.civAvatar);
            mCLParent = (ConstraintLayout) itemView.findViewById(R.id.clParent);
            mCIVAvatar.setOnClickListener(new View.OnClickListener() {
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
     * Interface for fragment
     */
    public interface OnItemsListener {
        void showDialogChoice();
    }
}
