package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * Created by at-dinhvo on 12/06/2017.
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;

    private LinearLayout mLnHeader;

    private List<DrawerItem> mTitles;
    private Context mContext;
    private Bitmap mBitmap;
    private OnItemClickListener mOnItemClickListener;
    private WallpaperManager mWallpaperManager;
    private Drawable mDrawable;

    public DrawerAdapter(Context context, List<DrawerItem> titles, OnItemClickListener onItemClickListener) {
        mTitles = titles;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
        setBackgroundHeader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.header_drawer, parent, false));
            default:
                return new TitleViewHolder(inflater.inflate(R.layout.item_list_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.mTvTitle.setText(mTitles.get(position).getTitle());
            if (mTitles.get(position).isSelected()) {
                titleViewHolder.mTvTitle.setTextColor(Color.BLUE);
            } else {
                titleViewHolder.mTvTitle.setTextColor(Color.WHITE);
            }
            titleViewHolder.mLinearLayout.setSelected(mTitles.get(position).isSelected());
        } else {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(R.string.header_name);
            headerViewHolder.mTvEmail.setText(R.string.header_email);
            if (mBitmap != null) {
                headerViewHolder.mImgAvatar.setImageBitmap(mBitmap);
            }
            setBackgroundHeader();
        }
    }

    private void setBackgroundHeader() {
        mWallpaperManager = WallpaperManager.getInstance(mContext);
        mDrawable = mWallpaperManager.getDrawable();
        if (mLnHeader != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLnHeader.setBackground(mDrawable);
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
        return mTitles.size();
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private LinearLayout mLinearLayout;

        TitleViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvItemTitle);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lnItem);
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;
        private TextView mTvEmail;
        private ImageView mImgAvatar;

        HeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mLnHeader = (LinearLayout) itemView.findViewById(R.id.lnHeader);
            mImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onImageClick();
                    }
                }
            });
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    /**
     * interface set event change avatar
     */
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onImageClick();
    }
}
