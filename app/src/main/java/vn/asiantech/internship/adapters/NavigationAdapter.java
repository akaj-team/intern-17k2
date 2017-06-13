package vn.asiantech.internship.adapters;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by Administrator on 6/12/2017.
 */
public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private String[] mTitle;
    private String mName;
    private String mEmail;
    private Context mContext;

    public NavigationAdapter(Context context, String[] title, String name, String email) {
        mTitle = title;
        mName = name;
        mEmail = email;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mTitle == null ? 1 : mTitle.length + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_navigation, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder=(HeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(mName);
            headerViewHolder.mTvEmail.setText(mEmail);
            WallpaperManager wallpaperManager=WallpaperManager.getInstance(mContext);
            headerViewHolder.mLlHeader.setBackgroundDrawable(wallpaperManager.getDrawable());
        }else {
            ItemViewHolder itemViewHolder=(ItemViewHolder) holder;
            itemViewHolder.mTvItem.setText(mTitle[position-1]);
            if (itemViewHolder.mLlItem.isSelected()){
                itemViewHolder.mLlItem.setBackgroundColor(Color.GREEN);
            }else{
                itemViewHolder.mLlItem.setBackgroundColor(Color.parseColor("#87888C"));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTvItem;
        private LinearLayout mLlItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvItem = (TextView) itemView.findViewById(R.id.tvItem);
            mLlItem=(LinearLayout) itemView.findViewById(R.id.llItem);
            mLlItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.llItem:
                    ((OnClickItem)v.getContext()).click(getAdapterPosition()-1);
                    break;
            }
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvEmail;
        private LinearLayout mLlHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mLlHeader = (LinearLayout) itemView.findViewById(R.id.llHeader);
        }
    }
    public interface OnClickItem{
        void click(int position);
    }
}
