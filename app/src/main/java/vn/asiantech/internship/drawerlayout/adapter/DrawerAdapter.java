package vn.asiantech.internship.drawerlayout.adapter;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.drawerlayout.model.DrawerItem;


/**
 * Created by sony on 12/06/2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter {
    private List<DrawerItem> mDrawerItems = new ArrayList<>();
    private  OnItemClickListener mListener;
    public static final int TYPE_HEAD = 0;
    public static final int TYPE_LIST = 1;

    public DrawerAdapter(List<DrawerItem> drawerItems) {
        this.mDrawerItems = drawerItems;
    }

    public interface OnItemClickListener {
        void onItemClick( int position);
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
            myViewHeader.mImgAvata.setImageResource(R.mipmap.ic_avata);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(myViewHeader.mImgBackground.getContext());
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            myViewHeader.mImgBackground.setImageDrawable(wallpaperDrawable);
            return;
        }
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mTvName.setText(mDrawerItems.get(position - 1).getName());
            myViewHolder.mImgIcon.setImageResource(mDrawerItems.get(position - 1).getImage());
           if (mDrawerItems.get(position - 1).isState()) {
                myViewHolder.mLnItem.setBackgroundColor(ContextCompat.getColor(myViewHolder.itemView.getContext(),R.color.colorGreyDark));
            } else {
                myViewHolder.mLnItem.setBackgroundColor(ContextCompat.getColor(myViewHolder.itemView.getContext(),R.color.colorGreyLight));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDrawerItems.size() + 1;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        ImageView mImgIcon;
        LinearLayout mLnItem;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mLnItem = (LinearLayout) itemView.findViewById(R.id.lnItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null)
                        mListener.onItemClick( getLayoutPosition()-1);
                }
            });
        }
    }

    public static class MyViewHeader extends RecyclerView.ViewHolder {
        ImageView mImgAvata;
        ImageView mImgBackground;

        public MyViewHeader(final View itemView) {
            super(itemView);
            mImgAvata = (ImageView) itemView.findViewById(R.id.imgAvata);
            mImgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
        }
    }
}
