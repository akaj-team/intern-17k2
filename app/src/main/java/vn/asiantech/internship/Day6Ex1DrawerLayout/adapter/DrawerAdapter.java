package vn.asiantech.internship.Day6Ex1DrawerLayout.adapter;

import android.app.WallpaperManager;
import android.content.Context;
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
import vn.asiantech.internship.R;

/**
 * create RecyclerViewAdapter
 * Created by at-hoavo on 09/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.RecyclerViewHolder> {
    private List<String> mFunctions;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;
    private Context mContext;
    private int mPosition;
    private Drawable wallpaperDrawable;

    public DrawerAdapter(List<String> functions, Context context, OnRecyclerViewClickListener onRecyclerViewClickListener) {
        mFunctions = functions;
        mContext = context;
        mOnRecyclerViewClickListener = onRecyclerViewClickListener;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
        wallpaperDrawable = wallpaperManager.getDrawable();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_header_drawer, parent, false);
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
                holder.mImgAvatar.setImageDrawable(wallpaperDrawable);
                break;
            default:
                holder.mTvFunction.setText(mFunctions.get(position));
                if (position == mPosition) {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItemPressed));

                } else {
                    holder.mItemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItem));
                }
        }
    }

    @Override
    public int getItemCount() {
        return mFunctions.size();
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    /**
     * create RecyclerViewHolder
     */
    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvFunction;
        private View mItemView;
        private ImageView mImgAvatar;

        RecyclerViewHolder(View itemView, boolean check) {
            super(itemView);
            mItemView = itemView;
            if (check) {
                mTvFunction = (TextView) itemView.findViewById(R.id.tvFunction);
                mItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnRecyclerViewClickListener.onClick(getAdapterPosition(), true);
                    }
                });
            } else {
                mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
                mImgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnRecyclerViewClickListener.onClick(getAdapterPosition(), false);
                    }
                });
            }
        }
    }
}
