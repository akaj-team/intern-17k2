package vn.asiantech.internship.drawerlayout;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;

/**
 * Created by PC on 6/12/2017.
 * This class used to custom DrawerLayout
 */
public class DrawerApdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<DrawerItem> mItems;
    private Context mContext;
    private OnItemClickListener mListener;

    public DrawerApdater(Context context, List<vn.asiantech.internship.models.DrawerItem> items,
                         OnItemClickListener listener) {
        this.mItems = items;
        this.mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer, parent, false);
                return new DrawerLayoutHeader(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
                return new DrawerLayoutItem(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerLayoutItem) {
            DrawerLayoutItem item = (DrawerLayoutItem) holder;
            DrawerItem drawerItem = mItems.get(position - 1);
            item.mTvName.setText(drawerItem.getName());
            if (drawerItem.isSelected()) {
                item.mLlBackGround.setBackgroundColor(Color.GREEN);
            } else {
                item.mLlBackGround.setBackgroundColor(Color.WHITE);
            }
            return;
        }
        if (holder instanceof DrawerLayoutHeader) {
            DrawerLayoutHeader header = (DrawerLayoutHeader) holder;

            // TODO: 6/12/2017 dummy data
            header.mTvName.setText(R.string.user_name);
            header.mTVEmail.setText(R.string.email);
            Drawable wallpaper = WallpaperManager.getInstance(mContext).getDrawable();
            header.mImgHeaderBg.setImageBitmap(((BitmapDrawable) wallpaper).getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 1;
    }

    /**
     * This class used to custom list Item of DrawerLayout
     */
    private class DrawerLayoutItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private LinearLayout mLlBackGround;

        private DrawerLayoutItem(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvItemName);
            mLlBackGround = (LinearLayout) itemView.findViewById(R.id.llItemDrawer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition() - 1);
            }
        }
    }

    /**
     * This class used to custom header of DrawerLayout
     */
    private class DrawerLayoutHeader extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTVEmail;
        private ImageView mImgItemIcon;
        private ImageView mImgHeaderBg;

        private DrawerLayoutHeader(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTVEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgItemIcon = (ImageView) itemView.findViewById(R.id.imgAvatar);
            mImgHeaderBg = (ImageView) itemView.findViewById(R.id.imgHeaderBg);
            mImgItemIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgAvatar:
                    Toast.makeText(mContext, "Bấm vào avatar", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    /**
     * This interface used to handle DrawerLayoutItem onClick
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
