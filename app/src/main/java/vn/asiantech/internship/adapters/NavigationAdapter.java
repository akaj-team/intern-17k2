package vn.asiantech.internship.adapters;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by Administrator on 6/12/2017.
 */
public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int KEY_GALLERY = 0;
    public static final int KEY_CAMERA = 1;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private String[] mTitle;
    private String mName;
    private String mEmail;
    private Context mContext;
    private OnClickItem mOnClickItem;
    private Bitmap mBitmap;

    public NavigationAdapter(Context context, String[] title, String name, String email, OnClickItem onClickItem, Bitmap bitmap) {
        mTitle = title;
        mName = name;
        mEmail = email;
        mContext = context;
        mOnClickItem = onClickItem;
        mBitmap = bitmap;
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
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(mName);
            headerViewHolder.mTvEmail.setText(mEmail);
            headerViewHolder.mImgUser.setImageBitmap(mBitmap);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
            headerViewHolder.mLlHeader.setBackgroundDrawable(wallpaperManager.getDrawable());
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvItem.setText(mTitle[position - 1]);
//            if (itemViewHolder.mLlItem.isSelected()) {
//                itemViewHolder.mLlItem.setBackgroundColor(Color.GREEN);
//            } else {
//                itemViewHolder.mLlItem.setBackgroundColor(Color.parseColor("#87888C"));
//            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvItem;
        private LinearLayout mLlItem;

        private ItemViewHolder(View itemView) {
            super(itemView);
            mTvItem = (TextView) itemView.findViewById(R.id.tvItem);
            mLlItem = (LinearLayout) itemView.findViewById(R.id.llItem);
            mLlItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.llItem:
                    mOnClickItem.click(getAdapterPosition() - 1);
                    break;
            }
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTvEmail;
        private LinearLayout mLlHeader;
        private ImageView mImgUser;

        private HeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgUser = (ImageView) itemView.findViewById(R.id.imgUser);
            mLlHeader = (LinearLayout) itemView.findViewById(R.id.llHeader);
            mImgUser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Choose photo from:")
                    .setCancelable(true)
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mOnClickItem.onClickAvatar(KEY_CAMERA);
                        }
                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mOnClickItem.onClickAvatar(KEY_GALLERY);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public interface OnClickItem {
        void click(int position);

        void onClickAvatar(int key);
    }
}
