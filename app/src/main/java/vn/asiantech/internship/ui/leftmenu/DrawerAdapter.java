package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.model.DrawerItem;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIST = 1;

    private List<DrawerItem> mDrawerItems;
    private OnItemClickListener mOnItemClickListener;
    private Drawable mDrawable;

    public DrawerAdapter(Context context, List<DrawerItem> titles, OnItemClickListener onItemClickListener) {
        mDrawerItems = titles;
        mOnItemClickListener = onItemClickListener;
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        mDrawable = wallpaperManager.getDrawable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new ItemHeaderViewHolder(inflater.inflate(R.layout.header_drawable, parent, false));
            default:
                return new ItemViewHolder(inflater.inflate(R.layout.item_list_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvTitle.setText(mDrawerItems.get(position - 1).getTitle());
            if (mDrawerItems.get(position - 1).isSelect()) {
                itemViewHolder.mTvTitle.setTextColor(Color.BLUE);
            } else {
                itemViewHolder.mTvTitle.setTextColor(Color.WHITE);
            }
            itemViewHolder.mLinearLayout.setSelected(mDrawerItems.get(position - 1).isSelect());
        } else {
            ItemHeaderViewHolder headerViewHolder = (ItemHeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(R.string.header_name);
            headerViewHolder.mTvEmail.setText(R.string.header_email);
            if (headerViewHolder.mLnHeader != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    headerViewHolder.mLnHeader.setBackground(mDrawable);
                }
            }
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
        return mDrawerItems.size() + 1;
    }

    /**
     * ItemViewHolder
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private LinearLayout mLinearLayout;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.llItem);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition() - 1);
                    }
                }
            });
        }
    }

    /**
     * ItemHeaderViewHolder
     */
    class ItemHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvEmail;
        private LinearLayout mLnHeader;

        ItemHeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mLnHeader = (LinearLayout) itemView.findViewById(R.id.lnHeader);
        }
    }

    /**
     * interface set eventclick
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
