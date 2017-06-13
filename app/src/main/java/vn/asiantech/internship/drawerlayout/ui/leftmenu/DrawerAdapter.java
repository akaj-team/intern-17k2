package vn.asiantech.internship.drawerlayout.ui.leftmenu;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawerlayout.models.DrawerItem;

/**
 * Used to collect and display data to the View.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-12
 */
public class DrawerAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private static final int TYPE_LIST = 1;
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private OnItemClickListener mListener;

    public DrawerAdapter(List<DrawerItem> drawerItems) {
        this.mDrawerItems = drawerItems;
    }

    /**
     * Use to get click listenner for items in recyclerview
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAvatarClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEAD:
                return new MyViewHeader(inflater.inflate(R.layout.item_header, parent, false));
            default:
                return new MyViewHolder(inflater.inflate(R.layout.item_function_list, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_LIST;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHeader) {
            MyViewHeader myViewHeader = (MyViewHeader) holder;
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(myViewHeader.mImgBackground.getContext());
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            myViewHeader.mImgBackground.setImageDrawable(wallpaperDrawable);
            return;
        }
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mTvName.setText(mDrawerItems.get(position - 1).getName());
            if (mDrawerItems.get(position - 1).isState()) {
                myViewHolder.mTvName.setBackgroundResource(R.drawable.bg_press_item_list_function);
            } else {
                myViewHolder.mTvName.setBackgroundResource(R.drawable.bg_default_item_list_function);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size() + 1;
    }

    /**
     * Used to register for DrawerItem in item of drawerlayout
     */
    private class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvName;

        private MyViewHolder(final View itemView) {
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
     * Used to register for DrawerItem in header of drawerlayout
     */
    private class MyViewHeader extends RecyclerView.ViewHolder {
        private final CircularImageView mImgAvata;
        private final ImageView mImgBackground;

        private MyViewHeader(final View itemView) {
            super(itemView);
            mImgAvata = (CircularImageView) itemView.findViewById(R.id.crImgAvata);
            mImgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            mImgAvata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAvatarClick();
                    }
                }
            });
        }
    }
}
