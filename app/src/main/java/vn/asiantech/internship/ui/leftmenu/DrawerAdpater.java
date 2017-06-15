package vn.asiantech.internship.ui.leftmenu;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.asiantech.internship.R;
import vn.asiantech.internship.models.DrawerItem;
import vn.asiantech.internship.models.User;

/**
 * Created by Administrator on 6/12/2017.
 * this adapter to custom recyclerView in navigation
 */
public class DrawerAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int KEY_GALLERY = 0;
    public static final int KEY_CAMERA = 1;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 0;
    private List<DrawerItem> mDrawerItems;
    private OnClickItemListener mOnClickItemListener;
    private List<User> mUsers;
    private Drawable mDrawable;


    public DrawerAdpater(Context context, List<DrawerItem> drawerItems, List<User> users, OnClickItemListener onClickItemListener) {
        mDrawerItems = drawerItems;
        mOnClickItemListener = onClickItemListener;
        mUsers = users;
        mDrawable = WallpaperManager.getInstance(context).getDrawable();
    }

    @Override
    public int getItemCount() {
        return mDrawerItems == null ? 1 : mDrawerItems.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.mTvName.setText(mUsers.get(0).getName());
            headerViewHolder.mTvEmail.setText(mUsers.get(0).getEmail());
            headerViewHolder.mImgUser.setImageBitmap(mUsers.get(0).getImgUser());
            headerViewHolder.mImgBackground.setImageDrawable(mDrawable);
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.mTvItem.setText(mDrawerItems.get(position - 1).getName());
            if (mDrawerItems.get(position - 1).isSelected()) {
                itemViewHolder.mTvItem.setTextColor(ContextCompat.getColor((itemViewHolder.mTvItem.getContext()), R.color.item_clicked));
            } else {
                itemViewHolder.mTvItem.setTextColor(ContextCompat.getColor((itemViewHolder.mTvItem.getContext()), R.color.white));
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

    /**
     * custom ViewHolder for another item
     */
    private final class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
                    if (mOnClickItemListener != null) {
                        mOnClickItemListener.onClickItem(getAdapterPosition() - 1);
                    }
                    break;
            }
        }
    }

    /**
     * custom ViewHolder for header
     */
    private final class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvName;
        private TextView mTvEmail;
        private ImageView mImgBackground;
        private CircleImageView mImgUser;

        private HeaderViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mTvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            mImgUser = (CircleImageView) itemView.findViewById(R.id.imgUser);
            mImgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            mImgUser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String[] dialogItems = v.getContext().getResources().getStringArray(R.array.dialog_item);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle(R.string.dialog_title)
                    .setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (mOnClickItemListener != null) {
                                        mOnClickItemListener.onClickAvatar(KEY_GALLERY);
                                    }
                                    break;
                                case 1:
                                    if (mOnClickItemListener != null) {
                                        mOnClickItemListener.onClickAvatar(KEY_CAMERA);
                                    }
                                    break;
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * interface set event click item
     */
    public interface OnClickItemListener {
        void onClickItem(int position);

        void onClickAvatar(int key);
    }
}
