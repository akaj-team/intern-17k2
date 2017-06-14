package vn.asiantech.internship;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Thanh Thien on 6/12/17
 * Using for Drawer
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEAD = 0;

    private Context mContext;
    private String[] mItems;
    private int mPositionSelected = -1; // mPositionSelected = -1 if nothing select
    private Bitmap mBitmap;
    private MainActivity.MainActivityInterface mMainActivityInterface;

    public DrawerAdapter(Context context, String[] items, MainActivity.MainActivityInterface mainActivityInterface) {
        this.mItems = items;
        this.mContext = context;
        this.mMainActivityInterface = mainActivityInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        switch (getItemViewType(i)) {
            case TYPE_HEAD:
                return new MyViewHolderHeader(LayoutInflater.from(mContext).inflate(R.layout.drawer_head, viewGroup, false));
            default:
                return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_drawer, viewGroup, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.mTextView.setText(mItems[i]);
            if (i == mPositionSelected) {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItemChoise));
            } else {
                viewHolder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorItem));
            }
        } else if (viewHolder instanceof MyViewHolderHeader) {
            MyViewHolderHeader myViewHolder = (MyViewHolderHeader) viewHolder;

            //Get current wallpaper
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();

            myViewHolder.mCLParent.setBackgroundDrawable(wallpaperDrawable);
            myViewHolder.mTvAuthorName.setText(mContext.getString(R.string.app_author));

            if (mBitmap != null) {
                myViewHolder.mImgAvatar.setImageBitmap(mBitmap);
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
    public void setPositionSelected(int positionSelected) {
        this.mPositionSelected = positionSelected;
        notifyDataSetChanged();
    }

    /**
     * @param bitmap is link of image
     */
    public void setImageAvatar(Bitmap bitmap) {
        this.mBitmap = bitmap;
        notifyDataSetChanged();
    }

    /**
     * ItemView for content list
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    /**
     * View for header
     */
    public class MyViewHolderHeader extends RecyclerView.ViewHolder {

        private TextView mTvAuthorName;
        private ImageView mImgAvatar;
        private ConstraintLayout mCLParent;

        MyViewHolderHeader(final View itemView) {
            super(itemView);
            mTvAuthorName = (TextView) itemView.findViewById(R.id.tvAuthorName);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mCLParent = (ConstraintLayout) itemView.findViewById(R.id.clParent);
            mImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof MainActivity) {
                        ((MainActivity) mContext).showDialogChoice(mMainActivityInterface);
                    }
                }
            });
        }
    }
}
