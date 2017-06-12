package vn.asiantech.internship.adapters;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * Created by Hai on 6/12/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Context mContext;
    private List<DrawerItem> mItems = new ArrayList<>();
    private int mSelectedPosition = -1;

    public DrawerAdapter(Context context, List<DrawerItem> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO switch case
        View v;
        switch (viewType){
            case TYPE_ITEM:
                v = LayoutInflater.from(mContext).inflate(R.layout.item_list_recyclerview, parent, false);
                return new ItemViewHolder(v);
            case TYPE_HEADER:
                v = LayoutInflater.from(mContext).inflate(R.layout.nav_header_main, parent, false);
                return new HeaderHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.mTvItem.setText(mItems.get(position).getTitle());
            if (mSelectedPosition == position) {
                itemHolder.mTvItem.setTextColor(ContextCompat.getColor(mContext, R.color.textview_text_select_color));
            } else {
                itemHolder.mTvItem.setTextColor(ContextCompat.getColor(mContext, R.color.textview_text_color));
            }
            itemHolder.mTvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedPosition = pos;
                    notifyDataSetChanged();
                }
            });
            return;
        }
        if (holder instanceof HeaderHolder) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
            Drawable wallPaperDrawable = wallpaperManager.getDrawable();
            ((HeaderHolder) holder).mLlHeader.setBackground(wallPaperDrawable);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvItem = (TextView) itemView.findViewById(R.id.tvItem);
        }
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlHeader;

        HeaderHolder(View itemView) {
            super(itemView);
            mLlHeader = (LinearLayout) itemView.findViewById(R.id.llHeader);
        }
    }
}
