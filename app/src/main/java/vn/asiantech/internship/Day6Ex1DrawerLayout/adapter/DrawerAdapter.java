package vn.asiantech.internship.Day6Ex1DrawerLayout.adapter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.Day6Ex1DrawerLayout.event.OnRecyclerViewClickListener;
import vn.asiantech.internship.Day6Ex1DrawerLayout.ui.MainActivity;
import vn.asiantech.internship.Day6Ex1DrawerLayout.view.RoundImageView;
import vn.asiantech.internship.R;

/**
 * create RecyclerViewAdapter
 * Created by at-hoavo on 09/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.RecyclerViewHolder> {
    private List<String> mFunctions;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;
    private Context mContext;
    private Drawable mWallpaperDrawable;
    private boolean mCheckAvatar;
    private Bitmap mAvatarBitmap;
    private int mPosition;

    public DrawerAdapter(List<String> items, Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {
        mFunctions = items;
        mContext = context;
        mOnRecyclerViewClickListener = onRecyclerViewClickListener;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
        mWallpaperDrawable = wallpaperManager.getDrawable();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return 0;
            default:
                return 1;
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_drawer_header, parent, false);
                return new RecyclerViewHolder(viewONE, false);
            default:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_function_drawer, parent, false);
                return new RecyclerViewHolder(viewTWO, true);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                if (!mCheckAvatar) {
                    holder.mImgAvatar.setImageDrawable(mWallpaperDrawable);
                } else {
                    holder.mImgAvatar.setImageBitmap(mAvatarBitmap);
                }
                break;
            default:
                holder.mTvFunction.setText(mFunctions.get(position));
                if (position == mPosition) {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItemPressed));

                } else {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItem));
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mFunctions.size();
    }

    public void setCheckAvatar(boolean check){
        mCheckAvatar=check;
    }
    public void setBitMapAvatar(Bitmap avatarBitmap){
        mAvatarBitmap=avatarBitmap;
    }
    public void setPosition(int position){
        mPosition=position;
    }

    /**
     * create RecyclerViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvFunction;
        private View mItemView;
        private RoundImageView mImgAvatar;

        RecyclerViewHolder(View itemView, boolean check) {
            super(itemView);
            mItemView = itemView;
            if (check) {
                mTvFunction = (TextView) itemView.findViewById(R.id.tvFunction);
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRecyclerViewClickListener != null) {
                            mOnRecyclerViewClickListener.onClick(getAdapterPosition(), true);
                        }
                    }
                });
            } else {
                mImgAvatar = (RoundImageView) itemView.findViewById(R.id.imgAvatar);
                mImgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRecyclerViewClickListener != null) {
                            mOnRecyclerViewClickListener.onClick(getAdapterPosition(), false);
                        }
                    }
                });
            }
        }
    }
}
